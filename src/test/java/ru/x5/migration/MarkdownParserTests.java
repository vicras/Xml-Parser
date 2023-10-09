package ru.x5.migration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.x5.migration.dto.context.ParseContext;
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
    private MarkdownService inventoryService;

    @Test
    void parseFullFile() {
        ParseContext parseContext = reader.read(FILE_NAME);
        Assertions.assertNotNull(parseContext);
        Assertions.assertEquals(1, parseContext.getResult().getParsedTags().size());
    }

    @Test
    void markdownService() {
        var markdowns = inventoryService.parseXml(FILE_NAME);
        Assertions.assertEquals(5, markdowns.size());
        Assertions.assertNotNull(markdowns.get(2).getMarkdownId());
        System.out.println("End of test");
    }
}
