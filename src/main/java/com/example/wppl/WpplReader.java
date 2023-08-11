package com.example.wppl;

import com.example.wppl.dto.ParseResult;
import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLInputFactory;
import com.fasterxml.aalto.AsyncXMLStreamReader;
import com.fasterxml.aalto.stax.InputFactoryImpl;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class WpplReader {

    private final AsyncXMLInputFactory inputF;
    private final WpplParser wpplParser;

    public WpplReader(WpplParser wpplParser) {
        this.wpplParser = wpplParser;
        inputF = new InputFactoryImpl();
    }

    public ParseResult read(String filePath) throws IOException, XMLStreamException {
        var parseResult = new ParseResult();
        AsyncXMLStreamReader<AsyncByteArrayFeeder> parser = inputF.createAsyncForByteArray();
        parseResult = fullFileRead(filePath, parseResult, parser);
        parser.close();
        return parseResult;
    }

    public ParseResult fullFileRead(String filePath,
                                    ParseResult parseResult,
                                    AsyncXMLStreamReader<AsyncByteArrayFeeder> parser
    ) throws IOException {
        try (InputStream stream = Files.newInputStream(Paths.get(filePath))) {
            var allFileBytes = stream.readAllBytes();
            return wpplParser.handleFileBytes(allFileBytes, parseResult, parser);
        }
    }

    public ParseResult readFileLazy(String filePath,
                                    ParseResult parseResult,
                                    AsyncXMLStreamReader<AsyncByteArrayFeeder> parser
    ) throws IOException {
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            lines.forEach(line ->
                    wpplParser.handleFileBytes(line.getBytes(), parseResult, parser)
            );
            return parseResult;
        }
    }

    public ParseResult readFileLazyParallel(String filePath,
                                            ParseResult parseResult,
                                            AsyncXMLStreamReader<AsyncByteArrayFeeder> parser
    ) throws IOException {
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            lines.forEach(line ->
                    wpplParser.handleFileBytes(line.getBytes(), parseResult, parser)
            );
            return parseResult;
        }
    }
}
