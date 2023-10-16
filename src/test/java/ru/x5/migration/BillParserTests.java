package ru.x5.migration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.bill.Mt_chk;
import ru.x5.migration.dto.xml.zzfo.IDOC;
import ru.x5.migration.reader.XmlFileReader;
import ru.x5.migration.service.zzfo.ZzfoService;

@SpringBootTest
@Sql(scripts = "db-clean-bill.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BillParserTests {

    private static final String FILE_NAME = "examples/bill/bill0.xml";

    @Autowired
    @Qualifier("billXmlReader")
    private XmlFileReader reader;

    @Test
    void parseFullFile() {
        ParseContext parseContext = reader.read(FILE_NAME);
        Assertions.assertNotNull(parseContext);
        Assertions.assertEquals(1, parseContext.getResult().getParsedTags().size());
        var optMt = parseContext.getResult().peekLastTag();
        Assertions.assertTrue(optMt.isPresent());
        var mt_chk = (Mt_chk) optMt.get();
        Assertions.assertNotNull(mt_chk.headers);
        Assertions.assertNotNull(mt_chk.headers.header);
        Assertions.assertEquals(99, mt_chk.headers.header.size());
    }

    @Test
    void saveToDb() {
    }
}
