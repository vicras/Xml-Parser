package com.example.wppl;

import com.example.wppl.dto.context.ParseContext;
import com.example.wppl.reader.impl.AaltoBufferFileReader;
import com.example.wppl.reader.impl.AaltoFullFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InventoryParserTests {

    private static final String FILE_NAME = "examples/inventory/inventory.xml";

    @Autowired
    private AaltoFullFileReader reader;
    @Autowired
    private AaltoBufferFileReader bufferReader;

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
}
