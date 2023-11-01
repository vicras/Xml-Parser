package ru.x5.migration.domain.doc;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
public class DocDetail {
    private Integer idSap;
    private Integer idDepartment;
    private Integer idItem;
    private BigInteger idHeader;
    private Integer idParent;
    private Integer idMaster;
    private Integer idConsign;
    private Integer idTaxscheme;
    private BigDecimal qty;
    private BigDecimal price;
    private BigDecimal overhead;
    private Double vat;
    private Double discount;
    private Integer lineno;
    private String bewart;
    private LocalDate dtMaxRealization;
}
