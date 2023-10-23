package ru.x5.migration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.billv2.Root;
import ru.x5.migration.reader.XmlFileReader;
import ru.x5.migration.service.billv2.BillV2Service;

@SpringBootTest
@Sql(scripts = "db-clean-bill.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BillV2ParserTests {

    private static final String FILE_NAME = "examples/billv2/bill0.xml";

    @Autowired
    @Qualifier("billV2XmlReader")
    private XmlFileReader reader;

    @Autowired
    private BillV2Service service;

    @Test
    void parseFullFile() {
        ParseContext parseContext = reader.read(FILE_NAME);
        Assertions.assertNotNull(parseContext);
        Assertions.assertEquals(1, parseContext.getResult().getParsedTags().size());
        var optRoot = parseContext.getResult().peekLastTag();
        Assertions.assertTrue(optRoot.isPresent());
        var root = (Root) optRoot.get();
        Assertions.assertNotNull(root.chk);
        Assertions.assertEquals(9, root.chk.size());
        var chk = root.chk.get(0);
        Assertions.assertEquals("4060", chk.PLANT);
        Assertions.assertEquals("094355", chk.TIME);
        Assertions.assertEquals("0.00", chk.ZDISCOUNT);
        Assertions.assertEquals("4060001001000000000001175042022081009:43", chk.BONID);
    }

    @Test
    void saveToDb() {
        var proposals = service.parseXml(FILE_NAME);
        Assertions.assertEquals(9, proposals.size());
        Assertions.assertNotNull(proposals.get(2).getPlant());
        System.out.println("End of test");
    }
}
