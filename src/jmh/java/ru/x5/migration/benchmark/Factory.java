package ru.x5.migration.benchmark;

import ru.x5.migration.creator.markdown.MarkdownCreator;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.context.ParseResult;
import ru.x5.migration.dto.xml.markdown.Mt_mkd;
import ru.x5.migration.parallel.TagsDivideDecider;
import ru.x5.migration.parallel.XmlFileDivider;
import ru.x5.migration.parallel.collector.MarkdownCollector;
import ru.x5.migration.reader.XmlFileReader;
import ru.x5.migration.reader.handler.XmlStaxEventHandler;
import ru.x5.migration.reader.impl.AaltoFullFileReader;
import ru.x5.migration.reader.impl.AaltoParallelFileReader;
import ru.x5.migration.reader.parser.AaltoAsyncParser;

import java.util.Map;
import java.util.stream.Collector;

public class Factory {
    public static XmlFileReader markdownXmlReader() {
        var staxHandler = new XmlStaxEventHandler(new MarkdownCreator());
        var aaltoAsyncParser = new AaltoAsyncParser(staxHandler);
        return new AaltoFullFileReader(aaltoAsyncParser);
    }

    public static XmlFileReader markdownXmlParallelReader() {
        var staxHandler = new XmlStaxEventHandler(new MarkdownCreator());
        var aaltoAsyncParser = new AaltoAsyncParser(staxHandler);
        var fileDivider = new XmlFileDivider() {
            private final MarkdownCollector markdownCollector = new MarkdownCollector();

            @Override
            public Collector<ParseContext, ParseResult, ParseContext> getFileCollector() {
                return markdownCollector;
            }

            @Override
            public TagsDivideDecider getTagDivideDecider() {
                return (line) -> line.contains("<row ");
            }

            @Override
            public ParseContext getInitialParseResult() {
                var context = new ParseContext();
                context.getResult().addTag(new Mt_mkd());
                context.getPath().newTag("mt_mkd", Map.of());
                return context;
            }
        };
        return new AaltoParallelFileReader(aaltoAsyncParser, fileDivider, 2);
    }
}
