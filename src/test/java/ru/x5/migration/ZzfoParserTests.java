package ru.x5.migration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.zzfo.IDOC;
import ru.x5.migration.reader.XmlFileReader;
import ru.x5.migration.service.zzfo.ZzfoService;

@SpringBootTest
//@Sql(scripts = "db-clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ZzfoParserTests {

    private static final String FILE_NAME = "examples/zzfo/zzfo0.xml";

    @Autowired
    @Qualifier("zzfoXmlReader")
    private XmlFileReader reader;
    @Autowired
    private ZzfoService service;

    @Test
    void parseFullFile() {
        ParseContext parseContext = reader.read(FILE_NAME);
        Assertions.assertNotNull(parseContext);
        Assertions.assertEquals(1, parseContext.getResult().getParsedTags().size());
        var optIdoc = parseContext.getResult().peekLastTag();
        Assertions.assertTrue(optIdoc.isPresent());
        var idoc = (IDOC) optIdoc.get();
        Assertions.assertNotNull(idoc.zproposal_item);
        Assertions.assertEquals(45, idoc.zproposal_item.size());
        Assertions.assertNotNull(idoc.edi_dc40);
    }

    @Test
    void saveToDb() {
        var proposals = service.parseXml(FILE_NAME);
        Assertions.assertEquals(45, proposals.size());
        Assertions.assertNotNull(proposals.get(2).getDelivtst());
        System.out.println("End of test");
    }
}
