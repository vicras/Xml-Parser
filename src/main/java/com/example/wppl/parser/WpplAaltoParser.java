package com.example.wppl.parser;

import com.example.wppl.dto.ParseResult;
import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLStreamReader;

public interface WpplAaltoParser {
    ParseResult handleFileBytes(byte[] allFileBytes,
                                ParseResult parseResult,
                                AsyncXMLStreamReader<AsyncByteArrayFeeder> parser
    );
}
