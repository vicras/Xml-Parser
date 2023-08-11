package com.example.wppl;

import com.example.wppl.dto.ParseResult;
import com.example.wppl.reader.impl.WppParallelReader;
import com.example.wppl.reader.impl.WpplBufferFileReader;
import com.example.wppl.reader.impl.WpplFullFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = "db-clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class WpplPerformanceTests {

    private static final String FILE_NAME = "WP_PL.xml";

    @Autowired
    private WppParallelReader parallelreader;
    @Autowired
    private WpplFullFileReader fullFileReader;
    @Autowired
    private WpplBufferFileReader bufferFileReader;

    @Test
    void parseFileParallel() throws Exception {
         var elapsedTime = measureTimeInMillis(()->parallelreader.read(FILE_NAME));
        System.out.println("Consumed time for reading with ParallelReader: " + elapsedTime + " millis");
    }

    @Test
    void parseFullFile() throws Exception {
        var elapsedTime = measureTimeInMillis(()->fullFileReader.read(FILE_NAME));
        System.out.println("Consumed time for reading with FullFileReader: " + elapsedTime + " millis");
    }

    @Test
    void parseWithBufferFile() throws Exception {
        var elapsedTime = measureTimeInMillis(()->bufferFileReader.read(FILE_NAME));
        System.out.println("Consumed time for reading with BufferFileReader: " + elapsedTime + " millis");
    }

    private long measureTimeInMillis(ExceptionalRunnable runnable) throws Exception {
        long start = System.currentTimeMillis();
        runnable.run();
        long finish = System.currentTimeMillis();
        return finish - start;
    }

    private interface ExceptionalRunnable{
        void run() throws Exception;
    }
}
