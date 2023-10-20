package ru.x5.migration.domain.billv2;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
public class Bill {
    private Integer plant;
    private String distrChan;
    private Integer rpaWid;
    private BigInteger zchhId;
    private Integer zttype;
    private double rpaSat; // ?
    private Integer businessdaydate;
    private BigInteger zchftim; //?
    private boolean storno;
    private BigInteger zcustnum; //?
    private Integer zpostyp;
    private String material; //? Integer "S000004"
    private String zetyp;
    private Integer zeanupc;
    private Double rpaRlq; //?
    private String salesUnit;
    private Double baseQty; //?
    private String baseUom;
    private String rpaSatPos; //? double "0.59-"
    private String normalamt; //? double "0.59-"
    private String zprice; //? double "0.59-"
    private Integer zrtaction;
    private Integer zpayTyp;
    private Integer zdistype;
    private Integer zdiscnum;
    private Integer rpaDid;
    private Double zdiscount; //?
    private Double zdiscamnt; //?
    private Double zpriceold; //?
    private String zscanType;
    private Integer datamatrix;
    private LocalDate dateosg;
}
