package ru.x5.migration.domain.inventory;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
public class ZloInvTaskZone {
    private BigInteger parentid;
    private String xblni;
    private String sndprn;
    private String artnr;
    private String zonename;
    private Double zoneqty;
    private LocalDateTime dateCreated;
}
