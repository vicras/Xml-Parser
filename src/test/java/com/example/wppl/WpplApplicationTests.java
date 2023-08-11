package com.example.wppl;

import com.example.wppl.dto.ParseResult;
import com.example.wppl.service.WpplService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WpplApplicationTests {

    private static final String FILE_NAME = "WP_PL.xml";
    @Autowired
    private WppParallelReader parallelreader;
    @Autowired
    private WpplReader reader;
    @Autowired
    private WpplService service;

    @Test
    void parseFile() throws Exception {
        ParseResult parseResult = reader.read(FILE_NAME);
        Assertions.assertNotNull(parseResult);
        Assertions.assertEquals(2, parseResult.e1WPA01s.size());
        Assertions.assertEquals(4, parseResult.ediDc40s.size());
    }

    @Test
    void parseFileParallel() throws Exception {

        ParseResult parseResult = parallelreader.read(FILE_NAME);
        Assertions.assertNotNull(parseResult);
        Assertions.assertEquals(2, parseResult.e1WPA01s.size());
        Assertions.assertEquals(4, parseResult.ediDc40s.size());
    }

    @Test
    public void saveToDatabase() throws Exception{
        Assertions.assertEquals(3, service.parseFileAndSaveRecords(FILE_NAME).size());
    }
}
