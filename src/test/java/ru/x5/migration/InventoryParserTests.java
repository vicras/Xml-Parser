package ru.x5.migration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.x5.migration.dao.inventory.ZloInvTaskHDao;
import ru.x5.migration.domain.inventory.ZloInvTaskH;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.inventory.IDOC;
import ru.x5.migration.reader.impl.AaltoBufferFileReader;
import ru.x5.migration.reader.impl.AaltoFullFileReader;
import ru.x5.migration.service.inventory.InventoryService;

import java.util.List;
import java.util.stream.IntStream;

import static java.math.BigInteger.ONE;

@SpringBootTest
@Sql(scripts = "db-clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class InventoryParserTests {

    private static final String FILE_NAME = "examples/inventory/inventory2.xml";
    private static final String BATCH_FILE_PREFIX_NAME = "examples/inventory/test/DATA_B";

    @Autowired
    private AaltoFullFileReader reader;
    @Autowired
    private AaltoBufferFileReader bufferReader;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ZloInvTaskHDao zloInvTaskHDao;

    @Test
    void parseFullFile() {
        ParseContext parseContext = reader.read(FILE_NAME);
        Assertions.assertNotNull(parseContext);
        Assertions.assertEquals(1, parseContext.getResult().getParsedTags().size());
    }

    @Test
    void parseWithBufferReader() {
        ParseContext parseContext = bufferReader.read(FILE_NAME);
        Assertions.assertNotNull(parseContext);
        Assertions.assertEquals(1, parseContext.getResult().getParsedTags().size());
    }

    @Test
    void inventoryService() {
        inventoryService.parseXml(FILE_NAME);
        System.out.println("End of test");
    }

    @Test
    void testTaskHSeq() {
        ZloInvTaskH first = new ZloInvTaskH();
        ZloInvTaskH second = new ZloInvTaskH();
        var list = List.of(first, second);
        zloInvTaskHDao.enhanceWithIds(list);
        Assertions.assertNotNull(first.getId());
        Assertions.assertNotNull(second.getId());
        Assertions.assertEquals(ONE, second.getId().subtract(first.getId()));
        System.out.println("End of test");
    }

    @Test
    void testBatchOfFiles() {
        var idocs = IntStream.range(0, 15)
                .mapToObj(fileNum -> bufferReader.read(BATCH_FILE_PREFIX_NAME + fileNum))
                .map(context -> (IDOC) context.getResult().getParsedTags().pollFirst())
                .toList();
        inventoryService.saveIdocs(idocs);
    }
}
