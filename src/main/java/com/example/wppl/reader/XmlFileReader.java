package com.example.wppl.reader;

import com.example.wppl.dto.context.ParseContext;

public interface XmlFileReader {
    ParseContext read(String filePath);
}
