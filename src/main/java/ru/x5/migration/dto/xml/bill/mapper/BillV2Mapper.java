package ru.x5.migration.dto.xml.bill.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.x5.migration.domain.bill.Bill;
import ru.x5.migration.domain.inventory.TaskHOrdering;
import ru.x5.migration.dto.xml.bill.Chk;

import java.time.LocalDateTime;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR, imports = {LocalDateTime.class, TaskHOrdering.class})
public abstract class BillV2Mapper {

    @Mapping(target = "plant", source = "PLANT")
    @Mapping(target = "distrChan", source = "DISTR_CHAN")
    @Mapping(target = "rpaWid", source = "RPA_WID")
    @Mapping(target = "zchhId", source = "ZCHH_ID")
    @Mapping(target = "zttype", source = "ZTTYPE")
    @Mapping(target = "rpaSat", source = "RPA_SAT")
    @Mapping(target = "businessdaydate", source = "BUSINESSDAYDATE", dateFormat = "yyyyMMdd")
    @Mapping(target = "zchftim", source = "ZCHFTIM", dateFormat = "yyyyMMddHHmmss")
    @Mapping(target = "storno", source = "STORNO")
    @Mapping(target = "zcustnum", source = "ZCUSTNUM")
    @Mapping(target = "zpostyp", source = "ZPOSTYP")
    @Mapping(target = "material", source = "MATERIAL")
    @Mapping(target = "zetyp", source = "ZETYP")
    @Mapping(target = "zeanupc", source = "ZEANUPC")
    @Mapping(target = "rpaRlq", source = "RPA_RLQ")
    @Mapping(target = "salesUnit", source = "SALES_UNIT")
    @Mapping(target = "baseQty", source = "BASE_QTY")
    @Mapping(target = "baseUom", source = "BASE_UOM")
    @Mapping(target = "rpaSatPos", source = "RPA_SAT_POS", qualifiedByName = "filterDash")
    @Mapping(target = "normalamt", source = "NORMALAMT", qualifiedByName = "filterDash")
    @Mapping(target = "zprice", source = "ZPRICE", qualifiedByName = "filterDash")
    @Mapping(target = "zrtaction", source = "ZRTACTION")
    @Mapping(target = "zpayTyp", source = "ZPAY_TYP")
    @Mapping(target = "zdistype", source = "ZDISTYPE")
    @Mapping(target = "zdiscnum", source = "ZDISCNUM")
    @Mapping(target = "rpaDid", source = "RPA_DID")
    @Mapping(target = "zdiscount", source = "ZDISCOUNT")
    @Mapping(target = "zdiscamnt", source = "ZDISCAMNT")
    @Mapping(target = "zpriceold", source = "ZPRICEOLD")
    @Mapping(target = "zscanType", source = "ZSCAN_TYPE")
    @Mapping(target = "datamatrix", source = "DATAMATRIX")
    @Mapping(target = "dateosg", source = "DATEOSG")
    public abstract Bill toBill(Chk chk);

    @Named("filterDash")
    protected String filterDoubleValuesFromDash(String value) {
        return value.replace("-", "");
    }
}
