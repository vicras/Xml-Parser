package com.example.wppl.reader.impl;

import com.example.wppl.dto.context.ParseContext;
import com.example.wppl.reader.XmlFileReader;
import com.example.wppl.reader.parser.AaltoAsyncParser;
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
public class AaltoFullFileReader implements XmlFileReader {

    private final AsyncXMLInputFactory asyncXMLInputFactory;
    private final AaltoAsyncParser aaltoAsyncParser;

    public AaltoFullFileReader(AaltoAsyncParser aaltoAsyncParser) {
        this.aaltoAsyncParser = aaltoAsyncParser;
        asyncXMLInputFactory = new InputFactoryImpl();
    }

    @Override
    public ParseContext read(String filePath) {
        try {

            var asyncXmlReader = asyncXMLInputFactory.createAsyncForByteArray();
            var parseContext = new ParseContext();
            parseContext = fullFileRead(filePath, parseContext, asyncXmlReader);
            asyncXmlReader.close();
            return parseContext;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public ParseContext fullFileRead(String filePath,
                                     ParseContext parseContext,
                                     AsyncXMLStreamReader<AsyncByteArrayFeeder> reader
    ) throws IOException {
        try (InputStream stream = Files.newInputStream(Paths.get(filePath))) {
            var allFileBytes = stream.readAllBytes();
            return aaltoAsyncParser.handleFileBytes(allFileBytes, parseContext, reader);
        }
    }
}
