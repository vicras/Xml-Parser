package com.example.wppl.reader;

import com.example.wppl.dto.ParseResult;

public interface WpplReader {
    ParseResult read(String filePath) throws Exception;
}
