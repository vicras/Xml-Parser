package ru.x5.migration.reader.parser;

import ru.x5.migration.reader.handler.XmlStaxEventHandler;
import ru.x5.migration.dto.context.ParseContext;
import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLStreamReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.fasterxml.aalto.AsyncXMLStreamReader.EVENT_INCOMPLETE;
import static javax.xml.stream.XMLStreamConstants.*;

@Slf4j
@Component
public class AaltoAsyncParser{

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
        return xmlStaxEventHandler.handleElementStarting(context, tagName);
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
