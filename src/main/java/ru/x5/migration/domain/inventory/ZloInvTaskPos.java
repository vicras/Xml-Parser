package ru.x5.migration.domain.inventory;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
public class ZloInvTaskPos {
    private BigInteger parentid;
    private String xblni;
    private String sndprn;
    private String artnr;
    private Double rsollmg;
    private Integer erfmg;
    private String erfme;
    private Double fldval;
    private LocalDateTime datecreated;
}
