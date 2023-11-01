package ru.x5.migration.domain.doc;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum HeaderVat {
    INCLUDE(1),
    EXCLUDE(0);

    private final int dbName;

    HeaderVat(int name) {
        dbName = name;
    }

    public static HeaderVat of(Integer enumName) {
        if (Objects.isNull(enumName)) return null;
        return Arrays.stream(values()).filter(val -> val.dbName == enumName)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't recognize HeaderVat: " + enumName));
    }
}
