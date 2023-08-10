package com.example.wppl.domain.mapper;

import com.example.wppl.domain.E1WPA01;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNullElse;

@Component
public class E1WPA02_Filler {

    public E1WPA01.E1WPA02 fill(E1WPA01.E1WPA02 e1WPA01, String fieldName, String content) {
        E1WPA01.E1WPA02 domain = requireNonNullElse(e1WPA01, new E1WPA01.E1WPA02());
        if ("WARENGR".equalsIgnoreCase(fieldName)) {
            domain.WARENGR = content;
        }
        if ("VERPGEW".equalsIgnoreCase(fieldName)) {
            domain.VERPGEW = content;
        }
        if ("RABERLAUBT".equalsIgnoreCase(fieldName)) {
            domain.RABERLAUBT = content;
        }
        if ("PRDRUCK".equalsIgnoreCase(fieldName)) {
            domain.PRDRUCK = content;
        }
        if ("ARTIKANZ".equalsIgnoreCase(fieldName)) {
            domain.ARTIKANZ = content;
        }
        if ("MHDHB".equalsIgnoreCase(fieldName)) {
            domain.MHDHB = content;
        }
        return domain;
    }
}
