package com.example.wppl.reader.impl;

import com.example.wppl.parser.impl.WpplAaltoAsyncParser;
import com.example.wppl.dto.ParseResult;
import com.example.wppl.reader.WpplReader;
import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLInputFactory;
import com.fasterxml.aalto.AsyncXMLStreamReader;
import com.fasterxml.aalto.stax.InputFactoryImpl;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Component
public class WpplBufferFileReader implements WpplReader {

    private final AsyncXMLInputFactory inputF;
    private final WpplAaltoAsyncParser wpplAAltoAsyncParser;

    public WpplBufferFileReader(WpplAaltoAsyncParser wpplAAltoAsyncParser) {
        this.wpplAAltoAsyncParser = wpplAAltoAsyncParser;
        inputF = new InputFactoryImpl();
    }

    @Override
    public ParseResult read(String filePath) throws IOException, XMLStreamException {
        var parseResult = new ParseResult();
        AsyncXMLStreamReader<AsyncByteArrayFeeder> parser = inputF.createAsyncForByteArray();
        parseResult = readFileLazy(filePath, parseResult, parser);
        parser.close();
        return parseResult;
    }

    public ParseResult readFileLazy(String filePath,
                                    ParseResult parseResult,
                                    AsyncXMLStreamReader<AsyncByteArrayFeeder> parser
    ) throws IOException {
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            lines.forEach(line ->
                    wpplAAltoAsyncParser.handleFileBytes(line.getBytes(), parseResult, parser)
            );
            return parseResult;
        }
    }
}
