package ru.x5.migration.creator;

import ru.x5.migration.dto.context.ParseContext;

public interface EntityCreator {
    void newElement(ParseContext context);

    void endElement(ParseContext context);

    void setValue(ParseContext context);
}
