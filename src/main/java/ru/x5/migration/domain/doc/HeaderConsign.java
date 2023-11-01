package ru.x5.migration.domain.doc;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum HeaderConsign {
    OWNERSHIP(0),
    CONSIGNMENT(1);

    private final int dbName;

    HeaderConsign(int name) {
        dbName = name;
    }

    public static HeaderConsign of(Integer enumName) {
        if (Objects.isNull(enumName)) return null;
        return Arrays.stream(values()).filter(val -> val.dbName == enumName)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't recognize HeaderConsign: " + enumName));
    }
}
