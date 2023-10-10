package ru.x5.migration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.evsd.Mt_merc_suppl_upload_nq;
import ru.x5.migration.reader.XmlFileReader;
import ru.x5.migration.service.evsd.EvsdService;

@SpringBootTest
@Sql(scripts = "db-clean-evsd.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EvsdParserTests {

    private static final String FILE_NAME = "examples/evsd/evsd6.xml";

    @Autowired
    @Qualifier("evsdXmlReader")
    private XmlFileReader reader;

    @Autowired
    private EvsdService evsdService;

    @Test
    void parseFullFile() {
        ParseContext parseContext = reader.read(FILE_NAME);
        Assertions.assertNotNull(parseContext);
        Assertions.assertEquals(1, parseContext.getResult().getParsedTags().size());
        var optIdoc = parseContext.getResult().peekLastTag();
        Assertions.assertTrue(optIdoc.isPresent());
        var uploadNq = (Mt_merc_suppl_upload_nq) optIdoc.get();
        Assertions.assertNotNull(uploadNq.body);
        Assertions.assertEquals(34, uploadNq.body.size());
        Assertions.assertNotNull(uploadNq.destination);
    }

    @Test
    void markdownService() {
        var buffers = evsdService.parseXml(FILE_NAME);
        Assertions.assertEquals(34, buffers.size());
        Assertions.assertNotNull(buffers.get(0).getDtInsert());
        System.out.println("End of test");
    }
}
