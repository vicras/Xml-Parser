package ru.x5.migration.domain.doc;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum HeaderStatus {
    BUFFER("B"),
    OPEN("O"),
    SPACE(" "),
    STORNO("S"),
    ALIVE("R"),
    CHECKED("VW"),
    BLOCKED("U"),
    DEPRECATED("_");

    private String dbName;

    HeaderStatus(String name) {
        dbName = name;
    }

    public static HeaderStatus of(String enumName) {
        if (Objects.isNull(enumName)) return null;
        return Arrays.stream(values()).filter(val -> val.dbName.equalsIgnoreCase(enumName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't recognize HeaderStatus: " + enumName));
    }
}