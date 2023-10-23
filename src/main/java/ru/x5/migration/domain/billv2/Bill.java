package ru.x5.migration.domain.billv2;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Bill {
    private Integer plant;
    private String distrChan;
    private Integer rpaWid;
    private BigInteger zchhId;
    private Integer zttype;
    private Double rpaSat;
    private LocalDate businessdaydate;
    private LocalDateTime zchftim;
    private Boolean storno;
    private BigInteger zcustnum;
    private Integer zpostyp;
    private String material;
    private String zetyp;
    private Integer zeanupc;
    private Double rpaRlq;
    private String salesUnit;
    private Double baseQty;
    private String baseUom;
    private Double rpaSatPos;
    private Double normalamt;
    private Double zprice;
    private Double zrtaction;
    private Integer zpayTyp;
    private Integer zdistype;
    private Integer zdiscnum;
    private Integer rpaDid;
    private Double zdiscount;
    private Double zdiscamnt;
    private Double zpriceold;
    private String zscanType;
    private Integer datamatrix;
    private LocalDate dateosg;
}
