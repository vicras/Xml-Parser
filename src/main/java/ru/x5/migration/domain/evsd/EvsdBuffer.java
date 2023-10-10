package ru.x5.migration.domain.evsd;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class EvsdBuffer {
    private String plantCode;
    private String vetDocUUID;
    private String productCode;
    private String productItemGTIN;
    private String productName;
    private String wayBillNo;
    private LocalDate wayBillDate;
    private LocalDateTime dtInsert;
}
