package com.example.wppl.domain.mapper;

import com.example.wppl.domain.E1WPA01;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNullElse;

@Component
public class E1WPA03_Filler {

    public E1WPA01.E1WPA03 fill(E1WPA01.E1WPA03 e1WPA01, String fieldName, String content) {
        E1WPA01.E1WPA03 domain = requireNonNullElse(e1WPA01, new E1WPA01.E1WPA03());
        if ("QUALARTTXT".equalsIgnoreCase(fieldName)) {
            domain.QUALARTTXT = content;
        }
        if ("SPRASCODE".equalsIgnoreCase(fieldName)) {
            domain.SPRASCODE = content;
        }
        if ("TEXT".equalsIgnoreCase(fieldName)) {
            domain.TEXT = content;
        }
        if ("LFDNR".equalsIgnoreCase(fieldName)) {
            domain.LFDNR = content;
        }
        return domain;
    }
}
