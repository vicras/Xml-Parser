package com.example.wppl;

import com.example.wppl.dao.inventory.InventoryDao;
import com.example.wppl.domain.inventory.E1WVINH;
import com.example.wppl.domain.inventory.EDI_DC40;
import com.example.wppl.dto.context.ParseContext;
import com.example.wppl.reader.impl.AaltoBufferFileReader;
import com.example.wppl.reader.impl.AaltoFullFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = "db-clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class InventoryParserTests {

    private static final String FILE_NAME = "examples/inventory/inventory2.xml";

    @Autowired
    private AaltoFullFileReader reader;
    @Autowired
    private AaltoBufferFileReader bufferReader;
    @Autowired
    private InventoryDao inventoryDao;

    @Test
    void parseFullFile() throws Exception {
        ParseContext parseContext = reader.read(FILE_NAME);
        Assertions.assertNotNull(parseContext);
        Assertions.assertEquals(2, parseContext.getResult().getParsedTags().size());
    }

    @Test
    void parseWithBufferReader() throws Exception {
        ParseContext parseContext = bufferReader.read(FILE_NAME);
        Assertions.assertNotNull(parseContext);
        Assertions.assertEquals(2, parseContext.getResult().getParsedTags().size());
    }

    @Test
    void inventoryDao() throws Exception {
        ParseContext parseContext = bufferReader.read(FILE_NAME);
        var result = parseContext.getResult();
        var e12vinh = (E1WVINH)result.getParsedTags().pollFirst();
        var edidc40 = (EDI_DC40)result.getParsedTags().pollFirst();
        inventoryDao.saveInventory(edidc40, e12vinh);
        System.out.println("End of test");
    }
}
