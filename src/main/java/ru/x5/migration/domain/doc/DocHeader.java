package ru.x5.migration.domain.doc;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
public class DocHeader {
    private Integer idSap;
    private Integer idDepartment;
    private BigInteger idHeader;
    private Integer idDoctype;
    private Integer idClient;
    private HeaderStatus status;
    private HeaderCurrency currency;
    private LocalDate dtCreated;
    private LocalDate dtPayed;
    private BigInteger summ;
    private BigInteger overHead;
    private BigInteger docNumber;
    private String note;
    private HeaderConsign consign;
    private HeaderVat vat;
    private String memo;
    private BigInteger vat20;
    private BigInteger vat10;
    private Integer idReference;
    private Integer idCashtype;
    private LocalDate dtUpdate;
    private Integer isShipped;
    private Integer isDuty;
    private String axNote;
    private Integer idAxHeader;
    private Integer isPaid;
    private Integer qix;
    private BigDecimal vat18;
    private Integer ordIdHeader;
    private LocalDate dtDetailStart;
    private LocalDate dtDetailFinish;
    private BigInteger exportSap;
    private BigInteger idSapHeader;
    private String sapNote;
    private String idSapDoctype;
}
