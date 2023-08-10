package com.example.wppl.domain.mapper;

import com.example.wppl.domain.EDI_DC40;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNullElse;

@Component
public class EDI_DC40_Filler {
    public EDI_DC40 fill(EDI_DC40 ediDc40, String fieldName, String content) {
        EDI_DC40 domain = requireNonNullElse(ediDc40, new EDI_DC40());

        if ("TABNAM".equalsIgnoreCase(fieldName)) {
            domain.TABNAM = content;
        }
        if ("MANDT".equalsIgnoreCase(fieldName)) {
            domain.MANDT = content;
        }
        if ("DOCNUM".equalsIgnoreCase(fieldName)) {
            domain.DOCNUM = content;
        }
        if ("DOCREL".equalsIgnoreCase(fieldName)) {
            domain.DOCREL = content;
        }
        if ("STATUS".equalsIgnoreCase(fieldName)) {
            domain.STATUS = content;
        }
        if ("DIRECT".equalsIgnoreCase(fieldName)) {
            domain.DIRECT = content;
        }
        if ("OUTMOD".equalsIgnoreCase(fieldName)) {
            domain.OUTMOD = content;
        }
        if ("IDOCTYP".equalsIgnoreCase(fieldName)) {
            domain.IDOCTYP = content;
        }
        if ("MESTYP".equalsIgnoreCase(fieldName)) {
            domain.MESTYP = content;
        }
        if ("SNDPOR".equalsIgnoreCase(fieldName)) {
            domain.SNDPOR = content;
        }
        if ("SNDPRT".equalsIgnoreCase(fieldName)) {
            domain.SNDPRT = content;
        }
        if ("SNDPRN".equalsIgnoreCase(fieldName)) {
            domain.SNDPRN = content;
        }
        if ("RCVPOR".equalsIgnoreCase(fieldName)) {
            domain.RCVPOR = content;
        }
        if ("RCVPRT".equalsIgnoreCase(fieldName)) {
            domain.RCVPRT = content;
        }
        if ("RCVPRN".equalsIgnoreCase(fieldName)) {
            domain.RCVPRN = content;
        }
        if ("CREDAT".equalsIgnoreCase(fieldName)) {
            domain.CREDAT = content;
        }
        if ("CRETIM".equalsIgnoreCase(fieldName)) {
            domain.CRETIM = content;
        }
        if ("SERIAL".equalsIgnoreCase(fieldName)) {
            domain.SERIAL = content;
        }
        return domain;
    }

}
