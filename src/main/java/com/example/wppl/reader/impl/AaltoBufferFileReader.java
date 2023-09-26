package com.example.wppl.reader.impl;

import com.example.wppl.dto.context.ParseContext;
import com.example.wppl.reader.XmlFileReader;
import com.example.wppl.reader.parser.AaltoAsyncParser;
import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLInputFactory;
import com.fasterxml.aalto.AsyncXMLStreamReader;
import com.fasterxml.aalto.stax.InputFactoryImpl;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Component
public class AaltoBufferFileReader implements XmlFileReader {

    private final AsyncXMLInputFactory asyncXMLInputFactory;
    private final AaltoAsyncParser aaltoAsyncParser;

    public AaltoBufferFileReader(AaltoAsyncParser aaltoAsyncParser) {
        this.aaltoAsyncParser = aaltoAsyncParser;
        asyncXMLInputFactory = new InputFactoryImpl();
    }

    @Override
    public ParseContext read(String filePath) {
        try{
            var parseContext = new ParseContext();
            var streamReader = asyncXMLInputFactory.createAsyncForByteArray();
            parseContext = readFileLazy(filePath, parseContext, streamReader);
            streamReader.close();
            return parseContext;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public ParseContext readFileLazy(String filePath,
                                     ParseContext parseContext,
                                     AsyncXMLStreamReader<AsyncByteArrayFeeder> parser
    ) throws IOException {
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            lines.forEach(line ->
                    aaltoAsyncParser.handleFileBytes(line.getBytes(), parseContext, parser)
            );
            return parseContext;
        }
    }
}
