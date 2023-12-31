package com.example.wppl.reader.impl;

import com.example.wppl.dto.ParseResult;
import com.example.wppl.parser.WpplAaltoParser;
import com.example.wppl.reader.WpplReader;
import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLInputFactory;
import com.fasterxml.aalto.AsyncXMLStreamReader;
import com.fasterxml.aalto.stax.InputFactoryImpl;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Component
public class WppParallelReader implements WpplReader {

    private static final int PARSER_THREAD_NUMBERS = 2;

    private final AsyncXMLInputFactory inputF;
    private final WpplAaltoParser wpplAAltoAsyncParser;


    public WppParallelReader(WpplAaltoParser wpplAAltoAsyncParser) {
        this.wpplAAltoAsyncParser = wpplAAltoAsyncParser;
        inputF = new InputFactoryImpl();
    }

    @Override
    public ParseResult read(String filePath) {
        Queue<String> xmlElementQueue = new ConcurrentLinkedQueue<>();
        AtomicBoolean isFileRead = new AtomicBoolean(false);
        CompletableFuture.runAsync(() -> reader(filePath, xmlElementQueue, isFileRead));

        var parserFutures = Stream.generate(() -> CompletableFuture.supplyAsync(() -> parser(xmlElementQueue, isFileRead)))
                .limit(PARSER_THREAD_NUMBERS)
                .toList();
        CompletableFuture.allOf(parserFutures.toArray(CompletableFuture[]::new));
        var results = parserFutures.stream()
                .map(CompletableFuture::join)
                .toList();
        var ediDc40s = results.stream().flatMap(pr -> pr.ediDc40s.stream()).toList();
        var e1WPA01s = results.stream().flatMap(pr -> pr.e1WPA01s.stream()).toList();

        return new ParseResult(ediDc40s, e1WPA01s);
    }

    @SneakyThrows
    private void reader(String filePath,
                        Queue<String> xmlElementQueue,
                        AtomicBoolean isFileRead) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            StringBuilder oneObjectForParse = new StringBuilder();
            for (String line; (line = br.readLine()) != null; ) {
                if (isNeedToSkipLine(line)) continue;
                oneObjectForParse.append(line)
                        .append("\n");
                if (isClosedTableValueTag(line)) {
                    xmlElementQueue.add(oneObjectForParse.toString());
                    oneObjectForParse.setLength(0); // in most cases it should be more performance efficient than allocate the new one
                }
            }
        }
        isFileRead.set(true);
    }


    private boolean isNeedToSkipLine(String line) {
        return line.contains("<WP_PLU03") || line.contains("IDOC ") || line.contains("IDOC>") || line.contains("xml");
    }

    private boolean isClosedTableValueTag(String line) {
        return line.contains("/EDI_DC40") || line.contains("/E1WPA01");
    }

    @SneakyThrows
    private ParseResult parser(
            Queue<String> xmlElementQueue,
            AtomicBoolean isFileRead
    ) {
        AsyncXMLStreamReader<AsyncByteArrayFeeder> parser = inputF.createAsyncForByteArray();
        var parseResult = new ParseResult();
        wpplAAltoAsyncParser.handleFileBytes(("<parser" + UUID.randomUUID() + ">").getBytes(), parseResult, parser); // File should have only one root element
        while (!(isFileRead.get() && xmlElementQueue.isEmpty())) {
            var dataToPerform = xmlElementQueue.poll();
            if (isNull(dataToPerform)) continue;
            wpplAAltoAsyncParser.handleFileBytes(dataToPerform.getBytes(), parseResult, parser);
        }

        return parseResult;
    }
}
