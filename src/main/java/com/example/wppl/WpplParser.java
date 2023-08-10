package com.example.wppl;

import com.example.wppl.dto.ParseResult;
import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLStreamReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.fasterxml.aalto.AsyncXMLStreamReader.EVENT_INCOMPLETE;
import static javax.xml.stream.XMLStreamConstants.*;

@Slf4j
@Component
public class WpplParser {

    private final ActiveElementHandler activeElementHandler;

    public WpplParser(ActiveElementHandler activeElementHandler) {
        this.activeElementHandler = activeElementHandler;
    }

    @SneakyThrows
    public ParseResult handleFileBytes(byte[] allFileBytes, ParseResult parseResult, AsyncXMLStreamReader<AsyncByteArrayFeeder> parser) {
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

    private ParseResult handleElementStarting(AsyncXMLStreamReader<AsyncByteArrayFeeder> parser, ParseResult parseResult) {
        var tagName = parser.getLocalName();

        activeElementHandler.elementStart(parseResult, tagName);
        return parseResult;
    }

    private ParseResult handleCharacters(AsyncXMLStreamReader<AsyncByteArrayFeeder> parser, ParseResult parseResult) {
        var text = parser.getText();
        activeElementHandler.fill(parseResult, text);
        return parseResult;
    }

    private ParseResult handleElementFinishing(AsyncXMLStreamReader<AsyncByteArrayFeeder> parser, ParseResult parseResult) {
        var tagName = parser.getLocalName();

        activeElementHandler.elementFinished(parseResult, tagName);
        return parseResult;
    }
}
