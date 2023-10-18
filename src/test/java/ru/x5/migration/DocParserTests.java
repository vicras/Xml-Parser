package ru.x5.migration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.bill.Mt_chk;
import ru.x5.migration.dto.xml.doc.Mt_doc_conc;
import ru.x5.migration.reader.XmlFileReader;

@SpringBootTest
//@Sql(scripts = "db-clean-bill.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DocParserTests {

    private static final String FILE_NAME = "examples/doc/doc0.xml";

    @Autowired
    @Qualifier("docXmlReader")
    private XmlFileReader reader;

    @Test
    void parseFullFile() {
        ParseContext parseContext = reader.read(FILE_NAME);
        Assertions.assertNotNull(parseContext);
        Assertions.assertEquals(1, parseContext.getResult().getParsedTags().size());
        var optMt = parseContext.getResult().peekLastTag();
        Assertions.assertTrue(optMt.isPresent());
        var mt_chk = (Mt_doc_conc) optMt.get();
        Assertions.assertNotNull(mt_chk.doc);
        Assertions.assertEquals(12, mt_chk.doc.size());
    }
}
