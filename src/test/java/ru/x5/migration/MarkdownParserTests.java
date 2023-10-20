package ru.x5.migration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.markdown.Mt_mkd;
import ru.x5.migration.reader.XmlFileReader;
import ru.x5.migration.service.markdown.MarkdownService;

@SpringBootTest
@Sql(scripts = "db-clean-markdown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class MarkdownParserTests {

    private static final String FILE_NAME = "examples/markdown/markdown0.xml";

    @Autowired
    @Qualifier("markdownXmlReader")
    private XmlFileReader reader;

    @Autowired
    @Qualifier("markdownParallelXmlReader")
    private XmlFileReader parallelReader;
    @Autowired
    private MarkdownService inventoryService;

    @Test
    void parseFullFileWithDefaultReader() {
        testWithReader(reader);
    }

    @Test
    void parseFullFileWithParallelReader() {
        testWithReader(parallelReader);
    }

    private void testWithReader(XmlFileReader reader) {
        ParseContext parseContext = reader.read(FILE_NAME);
        Assertions.assertNotNull(parseContext);
        var optMtMkd = parseContext.getResult().peekLastTag();
        Assertions.assertTrue(optMtMkd.isPresent());
        var mtMkd = (Mt_mkd) optMtMkd.get();
        Assertions.assertFalse(mtMkd.row.isEmpty());
        Assertions.assertEquals(5, mtMkd.row.size());
        Assertions.assertNotNull(mtMkd.CREATEDATE);
        Assertions.assertNotNull(mtMkd.MSGTYPE);
        Assertions.assertNotNull(mtMkd.WERKS);
    }

    @Test
    void markdownService() {
        var markdowns = inventoryService.parseXml(FILE_NAME);
        Assertions.assertEquals(5, markdowns.size());
        Assertions.assertNotNull(markdowns.get(2).getMarkdownId());
        Assertions.assertNotNull(markdowns.get(2).getWerks());
        System.out.println("End of test");
    }
}
