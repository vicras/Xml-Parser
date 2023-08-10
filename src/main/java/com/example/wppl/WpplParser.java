package com.example.wppl;

import com.example.wppl.domain.mapper.E1WPA01_Filler;
import com.example.wppl.domain.mapper.E1WPA02_Filler;
import com.example.wppl.domain.mapper.E1WPA03_Filler;
import com.example.wppl.domain.mapper.EDI_DC40_Filler;
import com.example.wppl.dto.ParseResult;
import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLInputFactory;
import com.fasterxml.aalto.AsyncXMLStreamReader;
import com.fasterxml.aalto.stax.InputFactoryImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.fasterxml.aalto.AsyncXMLStreamReader.EVENT_INCOMPLETE;
import static javax.xml.stream.XMLStreamConstants.*;

@Slf4j
@Component
public class WpplParser {

    private final EDI_DC40_Filler ediDc40Filler;
    private final E1WPA01_Filler e1WPA01Filler;
    private final E1WPA02_Filler e1WPA02Filler;
    private final E1WPA03_Filler e1WPA03Filler;

    private final AsyncXMLInputFactory inputF;

    public WpplParser(EDI_DC40_Filler ediDc40Filler, E1WPA01_Filler e1WPA01Filler, E1WPA02_Filler e1WPA02Filler, E1WPA03_Filler e1WPA03Filler) {
        this.ediDc40Filler = ediDc40Filler;
        this.e1WPA01Filler = e1WPA01Filler;
        this.e1WPA02Filler = e1WPA02Filler;
        this.e1WPA03Filler = e1WPA03Filler;

        inputF = new InputFactoryImpl();
    }

    public ParseResult parse() throws Exception {
        var parseResult = new ParseResult(ediDc40Filler, e1WPA01Filler, e1WPA02Filler, e1WPA03Filler);
        AsyncXMLStreamReader<AsyncByteArrayFeeder> parser = inputF.createAsyncForByteArray();

        try (InputStream stream = Files.newInputStream(Paths.get("WP_PL.xml"))) {
            var allFileBytes = stream.readAllBytes();
            var result = handleFileBytes(allFileBytes, parseResult, parser);
            System.out.println(parseResult);
            parser.close();
            return parseResult;
        }
    }

    public ParseResult parse2() throws Exception {
        var parseResult = new ParseResult(ediDc40Filler, e1WPA01Filler, e1WPA02Filler, e1WPA03Filler);
        AsyncXMLStreamReader<AsyncByteArrayFeeder> parser = inputF.createAsyncForByteArray();

        try (Stream<String> lines = Files.lines(Path.of("WP_PL.xml"))) {
            lines.forEach(line ->
                    handleFileBytes(line.getBytes(), parseResult, parser)
            );
            System.out.println(parseResult);
            parser.close();
            return parseResult;
        }
    }

    @SneakyThrows
    private ParseResult handleFileBytes(byte[] allFileBytes, ParseResult parseResult, AsyncXMLStreamReader<AsyncByteArrayFeeder> parser) {
        AsyncByteArrayFeeder inputFeeder = parser.getInputFeeder();

        inputFeeder.feedInput(allFileBytes, 0, allFileBytes.length);

        while (parser.hasNext()) {
            int eventId = parser.next();
            if (START_DOCUMENT == eventId) {

            } else if (START_ELEMENT == eventId) {
                parseResult = handleElementStarting(parser, parseResult);
            } else if (CHARACTERS == eventId) {
                parseResult = handleCharacters(parser, parseResult);
            } else if (END_ELEMENT == eventId) {
                parseResult = handleElementFinishing(parser, parseResult);
            } else if (EVENT_INCOMPLETE == eventId) {
                return parseResult;
            } else {
                log.warn("The event with ID={} wasn't processed", eventId);
            }
        }
        return parseResult;
    }

    private static ParseResult handleElementStarting(AsyncXMLStreamReader<AsyncByteArrayFeeder> parser, ParseResult parseResult) {
        var tagName = parser.getLocalName();

        parseResult.elementStart(tagName);
        return parseResult;
    }

    private ParseResult handleCharacters(AsyncXMLStreamReader<AsyncByteArrayFeeder> parser, ParseResult parseResult) {
        var text = parser.getText();
        parseResult.fill(text);
        return parseResult;
    }

    private ParseResult handleElementFinishing(AsyncXMLStreamReader<AsyncByteArrayFeeder> parser, ParseResult parseResult) {
        var tagName = parser.getLocalName();

        parseResult.elementFinished(tagName);
        return parseResult;
    }
}
