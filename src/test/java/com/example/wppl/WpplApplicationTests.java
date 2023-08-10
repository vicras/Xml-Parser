package com.example.wppl;

import com.example.wppl.dto.ParseResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WpplApplicationTests {

    @Autowired
    private WppParallelReader parallelreader;
    @Autowired
    private WpplReader reader;

    @Test
    void parseFile() throws Exception {
        var fileName = "WP_PL.xml";
        ParseResult parseResult = reader.read(fileName);
        Assertions.assertNotNull(parseResult);
        Assertions.assertEquals(3, parseResult.e1WPA01s.size());
        Assertions.assertEquals(4, parseResult.ediDc40s.size());
    }

    @Test
    void parseFileParallel() throws Exception {
        var fileName = "WP_PL.xml";
        ParseResult parseResult = parallelreader.read(fileName);
        Assertions.assertNotNull(parseResult);
        Assertions.assertEquals(3, parseResult.e1WPA01s.size());
        Assertions.assertEquals(4, parseResult.ediDc40s.size());
    }
}
