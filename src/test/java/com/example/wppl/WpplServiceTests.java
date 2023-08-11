package com.example.wppl;

import com.example.wppl.service.WpplService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = "db-clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class WpplServiceTests {

    private static final String XML_FILE_NAME = "WP_PL.xml";
    private static final String SCHEMA_FILE_NAME = "scheme.xsd";

    @Autowired
    private WpplService service;

    @Test
    public void saveToDatabase() throws Exception {
        Assertions.assertEquals(2, service.parseFileAndSaveRecords(XML_FILE_NAME).size());
    }

    @Test
    public void validateXmlSchema() throws Exception {
        Assertions.assertTrue(service.isValidXmlFile(XML_FILE_NAME, SCHEMA_FILE_NAME));
    }

}
