package ru.x5.migration.domain.inventory;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ZloInvTaskH {
    private BigInteger id;
    private String xblni;
    private String sndprn;
    private String name;
    private LocalDate vpdat;
    private TaskHOrdering ordng;
    private Integer keord;
    private LocalDate zdact;
    private String ztact;
    private LocalDate zdate;
    private Integer ztime;
    private LocalDate ddate;
    private Integer dtime;
    private LocalDateTime datecreated;
}
