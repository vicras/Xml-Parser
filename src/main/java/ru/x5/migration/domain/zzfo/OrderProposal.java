package ru.x5.migration.domain.zzfo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderProposal {
    private String ordno;
    private String itmno;
    private String locnoto;
    private String matnr;
    private String locnofr;
    private String slsnr;
    private String maktx;
    private String matkl;
    private String quantity;
    private String unit;
    private String grquantity;

    private String grquantityUnit;
    private String difExists;
    private String excExists;
    private String statval;
    private String delivtst;
    private String plntst;
    private String openQuantity;

    private String averageSalesQuantity;
    private String conversionFactor;
    private String timeAutoconfirmation;
    private String orderType;
    private String maximum;

    private String presentationStock;
    private String purchPrice;
    private String zminweight;
    private String zminbw;
    private String budget;
    private String norder;
    private String ndlvry;
    private LocalDateTime dtInsert;
}
