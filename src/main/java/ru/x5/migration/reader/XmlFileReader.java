package ru.x5.migration.reader;

import ru.x5.migration.dto.context.ParseContext;

public interface XmlFileReader {
    ParseContext read(String filePath);
}
