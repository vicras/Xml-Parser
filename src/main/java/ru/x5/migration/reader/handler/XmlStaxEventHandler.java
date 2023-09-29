package ru.x5.migration.reader.handler;

import ru.x5.migration.creator.EntityCreator;
import ru.x5.migration.dto.context.ParseContext;

import java.util.Map;

public class XmlStaxEventHandler {

    private final EntityCreator creator;

    public XmlStaxEventHandler(EntityCreator creator) {
        this.creator = creator;
    }

    public ParseContext handleElementStarting(ParseContext context, String tagName, Map<String, String> attributes) {
        context.addNewTagName(tagName, attributes);
        creator.newElement(context);
        return context;
    }

    public ParseContext handleCharacters(ParseContext context, String content) {
        context.addNewTagContent(content);
        creator.setValue(context);
        return context;
    }

    public ParseContext handleElementFinishing(ParseContext context, String tagName) {
        creator.endElement(context);
        context.addEndTagName(tagName);
        return context;
    }
}
