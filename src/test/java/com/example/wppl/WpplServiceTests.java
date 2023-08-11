package com.example.wppl;

import com.example.wppl.dto.ParseResult;
import com.example.wppl.reader.impl.WppParallelReader;
import com.example.wppl.reader.impl.WpplFullFileReader;
import com.example.wppl.service.WpplService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = "db-clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class WpplServiceTests {

    private static final String FILE_NAME = "WP_PL.xml";

    @Autowired
    private WpplService service;

    @Test
    public void saveToDatabase() throws Exception{
        Assertions.assertEquals(2, service.parseFileAndSaveRecords(FILE_NAME).size());
    }
}
