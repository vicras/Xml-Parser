package ru.x5.migration.reader.parser;

import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLStreamReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.reader.handler.XmlStaxEventHandler;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.fasterxml.aalto.AsyncXMLStreamReader.EVENT_INCOMPLETE;
import static javax.xml.stream.XMLStreamConstants.*;

@Slf4j
public class AaltoAsyncParser {

    private final XmlStaxEventHandler xmlStaxEventHandler;

    public AaltoAsyncParser(XmlStaxEventHandler xmlStaxEventHandler) {
        this.xmlStaxEventHandler = xmlStaxEventHandler;
    }

    @SneakyThrows
    public ParseContext handleFileBytes(
            byte[] allFileBytes,
            ParseContext context,
            AsyncXMLStreamReader<AsyncByteArrayFeeder> streamReader
    ) {
        AsyncByteArrayFeeder inputFeeder = streamReader.getInputFeeder();
        inputFeeder.feedInput(allFileBytes, 0, allFileBytes.length);

        while (streamReader.hasNext()) {
            int eventId = streamReader.next();
            if (START_DOCUMENT == eventId) {
            } else if (START_ELEMENT == eventId) {
                handleElementStarting(streamReader, context);
            } else if (CHARACTERS == eventId) {
                handleCharacters(streamReader, context);
            } else if (END_ELEMENT == eventId) {
                handleElementFinishing(streamReader, context);
            } else if (EVENT_INCOMPLETE == eventId) {
                return context;
            } else {
                log.warn("The event with ID={} wasn't processed", eventId);
            }
        }
        return context;
    }

    private ParseContext handleElementStarting(
            AsyncXMLStreamReader<AsyncByteArrayFeeder> parser,
            ParseContext context
    ) {
        var tagName = parser.getLocalName();
        var attributes = getTagAttributseMap(parser);
        return xmlStaxEventHandler.handleElementStarting(context, tagName, attributes);
    }

    private Map<String, String> getTagAttributseMap(AsyncXMLStreamReader<AsyncByteArrayFeeder> reader) {
        return IntStream.range(0, reader.getAttributeCount())
                .boxed()
                .collect(
                        Collectors.toMap(
                                attrNum -> reader.getAttributeName(attrNum).getLocalPart(),
                                attrNum -> reader.getAttributeValue(attrNum).strip())
                );
    }

    private ParseContext handleCharacters(
            AsyncXMLStreamReader<AsyncByteArrayFeeder> parser,
            ParseContext context
    ) {
        var content = parser.getText().strip();
        return xmlStaxEventHandler.handleCharacters(context, content);
    }

    private ParseContext handleElementFinishing(
            AsyncXMLStreamReader<AsyncByteArrayFeeder> parser,
            ParseContext context
    ) {
        var tagName = parser.getLocalName();
        return xmlStaxEventHandler.handleElementFinishing(context, tagName);
    }
}
