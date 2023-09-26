package com.example.wppl.creator;

import com.example.wppl.dto.context.ParseContext;

public interface EntityCreator {
    void newElement(ParseContext context);

    void endElement(ParseContext context);

    void setValue(ParseContext context);
}
