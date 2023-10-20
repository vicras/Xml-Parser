package ru.x5.migration.parallel;

import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.context.ParseResult;

import java.util.stream.Collector;

public interface XmlFileDivider {
    Collector<ParseContext, ParseResult, ParseContext> getFileCollector();

    TagsDivideDecider getTagDivideDecider();

    ParseContext getInitialParseResult();
}
