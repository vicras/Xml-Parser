package com.example.wppl.domain.mapper;

import com.example.wppl.domain.E1WPA01;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNullElse;

@Component
public class E1WPA01_Filler {

    public E1WPA01 fill(E1WPA01 e1WPA01, String fieldName, String content) {
        E1WPA01 domain = requireNonNullElse(e1WPA01, new E1WPA01());
        if ("FILIALE".equalsIgnoreCase(fieldName)) {
            domain.FILIALE = content;
        }
        if ("AENDKENNZ".equalsIgnoreCase(fieldName)) {
            domain.AENDKENNZ = content;
        }
        if ("AKTIVDATUM".equalsIgnoreCase(fieldName)) {
            domain.AKTIVDATUM = content;
        }
        if ("AENDDATUM".equalsIgnoreCase(fieldName)) {
            domain.AENDDATUM = content;
        }
        if ("HAUPTEAN".equalsIgnoreCase(fieldName)) {
            domain.HAUPTEAN = content;
        }
        if ("ARTIKELNR".equalsIgnoreCase(fieldName)) {
            domain.ARTIKELNR = content;
        }
        if ("POSME".equalsIgnoreCase(fieldName)) {
            domain.POSME = content;
        }
        return domain;
    }
}
