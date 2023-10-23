package ru.x5.migration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.x5.migration.creator.bill.BillCreator;
import ru.x5.migration.creator.doc.DocCreator;
import ru.x5.migration.creator.evsd.EvsdCreator;
import ru.x5.migration.creator.inventory.InventoryCreator;
import ru.x5.migration.creator.markdown.MarkdownCreator;
import ru.x5.migration.creator.zzfo.ZzfoCreator;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.context.ParseResult;
import ru.x5.migration.dto.xml.markdown.Mt_mkd;
import ru.x5.migration.parallel.TagsDivideDecider;
import ru.x5.migration.parallel.XmlFileDivider;
import ru.x5.migration.parallel.collector.MarkdownCollector;
import ru.x5.migration.reader.XmlFileReader;
import ru.x5.migration.reader.handler.XmlStaxEventHandler;
import ru.x5.migration.reader.impl.AaltoBufferFileReader;
import ru.x5.migration.reader.impl.AaltoFullFileReader;
import ru.x5.migration.reader.impl.AaltoParallelFileReader;
import ru.x5.migration.reader.parser.AaltoAsyncParser;

import java.util.Map;
import java.util.stream.Collector;

@Configuration
public class XmlReaderFactory {

    @Bean("markdownXmlReader")
    public XmlFileReader markdownXmlReader(MarkdownCreator markdownCreator) {
        var staxHandler = new XmlStaxEventHandler(markdownCreator);
        var aaltoAsyncParser = new AaltoAsyncParser(staxHandler);
        return new AaltoFullFileReader(aaltoAsyncParser);
    }

    @Bean("markdownParallelXmlReader")
    public XmlFileReader markdownXmlParallelReader(MarkdownCreator markdownCreator) {
        var staxHandler = new XmlStaxEventHandler(markdownCreator);
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

    @Bean("docXmlReader")
    public XmlFileReader docXmlReader(DocCreator docCreator) {
        var staxHandler = new XmlStaxEventHandler(docCreator);
        var aaltoAsyncParser = new AaltoAsyncParser(staxHandler);
        return new AaltoFullFileReader(aaltoAsyncParser);
    }

    @Bean("billXmlReader")
    public XmlFileReader billV2XmlReader(BillCreator billCreator) {
        var staxHandler = new XmlStaxEventHandler(billCreator);
        var aaltoAsyncParser = new AaltoAsyncParser(staxHandler);
        return new AaltoFullFileReader(aaltoAsyncParser);
    }

    @Bean("zzfoXmlReader")
    public XmlFileReader zzfoXmlReader(ZzfoCreator zzfoCreator) {
        var staxHandler = new XmlStaxEventHandler(zzfoCreator);
        var aaltoAsyncParser = new AaltoAsyncParser(staxHandler);
        return new AaltoFullFileReader(aaltoAsyncParser);
    }

    @Bean("evsdXmlReader")
    public XmlFileReader evsdXmlReader(EvsdCreator evsdCreator) {
        var staxHandler = new XmlStaxEventHandler(evsdCreator);
        var aaltoAsyncParser = new AaltoAsyncParser(staxHandler);
        return new AaltoFullFileReader(aaltoAsyncParser);
    }

    @Bean("inventoryXmlReader")
    public XmlFileReader inventoryXmlReader(InventoryCreator inventoryCreator) {
        var staxHandler = new XmlStaxEventHandler(inventoryCreator);
        var aaltoAsyncParser = new AaltoAsyncParser(staxHandler);
        return new AaltoFullFileReader(aaltoAsyncParser);
    }

    @Bean("inventoryXmlBufferReader")
    public XmlFileReader inventoryBufferXmlReader(InventoryCreator inventoryCreator) {
        var staxHandler = new XmlStaxEventHandler(inventoryCreator);
        var aaltoAsyncParser = new AaltoAsyncParser(staxHandler);
        return new AaltoBufferFileReader(aaltoAsyncParser);
    }
}
