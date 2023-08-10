package com.example.wppl;

import com.example.wppl.dto.ParseResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest
class WpplApplicationTests {

    @Autowired
    private WpplParser parser;
    @Test
    void contextLoads() throws Exception {
        ParseResult parseResult = parser.parse2();
        Assertions.assertNotNull(parseResult);
        Assertions.assertEquals(3,parseResult.e1WPA01s.size());
        Assertions.assertEquals(4,parseResult.ediDc40s.size());
    }

}
