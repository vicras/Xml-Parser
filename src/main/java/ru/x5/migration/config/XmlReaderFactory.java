package ru.x5.migration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.x5.migration.creator.bill.BillCreator;
import ru.x5.migration.creator.evsd.EvsdCreator;
import ru.x5.migration.creator.inventory.InventoryCreator;
import ru.x5.migration.creator.markdown.MarkdownCreator;
import ru.x5.migration.creator.zzfo.ZzfoCreator;
import ru.x5.migration.reader.XmlFileReader;
import ru.x5.migration.reader.handler.XmlStaxEventHandler;
import ru.x5.migration.reader.impl.AaltoBufferFileReader;
import ru.x5.migration.reader.impl.AaltoFullFileReader;
import ru.x5.migration.reader.parser.AaltoAsyncParser;

@Configuration
public class XmlReaderFactory {

    @Bean("markdownXmlReader")
    public XmlFileReader markdownXmlReader(MarkdownCreator markdownCreator) {
        var staxHandler = new XmlStaxEventHandler(markdownCreator);
        var aaltoAsyncParser = new AaltoAsyncParser(staxHandler);
        return new AaltoFullFileReader(aaltoAsyncParser);
    }

    @Bean("billXmlReader")
    public XmlFileReader billXmlReader(BillCreator billCreator) {
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
