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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class WpplFullFileReader  implements WpplReader {

    private final AsyncXMLInputFactory inputF;
    private final WpplAaltoAsyncParser wpplAAltoAsyncParser;

    public WpplFullFileReader(WpplAaltoAsyncParser wpplAAltoAsyncParser) {
        this.wpplAAltoAsyncParser = wpplAAltoAsyncParser;
        inputF = new InputFactoryImpl();
    }

    @Override
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
            return wpplAAltoAsyncParser.handleFileBytes(allFileBytes, parseResult, parser);
        }
    }
}
