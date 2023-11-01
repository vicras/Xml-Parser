package ru.x5.migration.domain.doc;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum HeaderCurrency {
    RUB(0),
    DOLLAR(1);

    private final int dbName;

    HeaderCurrency(int name) {
        dbName = name;
    }

    public static HeaderCurrency of(Integer enumName) {
        if (Objects.isNull(enumName)) return null;
        return Arrays.stream(values()).filter(val -> val.dbName == enumName)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't recognize HeaderCurrency: " + enumName));
    }
}