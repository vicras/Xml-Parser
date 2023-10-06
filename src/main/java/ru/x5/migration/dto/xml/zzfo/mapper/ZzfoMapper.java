package ru.x5.migration.dto.xml.zzfo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.x5.migration.domain.zzfo.OrderProposal;
import ru.x5.migration.dto.xml.zzfo.EDI_DC40;
import ru.x5.migration.dto.xml.zzfo.ZPROPOSAL_ITEM;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public abstract class ZzfoMapper {

    @Mapping(target = "ordno", source = "zproposalItem.ORDNO")
    @Mapping(target = "itmno", source = "zproposalItem.ITMNO")
    @Mapping(target = "locnoto", source = "zproposalItem.LOCNOTO")
    @Mapping(target = "matnr", source = "zproposalItem.MATNR")
    @Mapping(target = "locnofr", source = "zproposalItem.LOCNOFR")
    @Mapping(target = "slsnr", ignore = true)
    @Mapping(target = "maktx", source = "zproposalItem.MAKTX")
    @Mapping(target = "matkl", source = "zproposalItem.MATKL")
    @Mapping(target = "quantity", source = "zproposalItem.QUANTITY")
    @Mapping(target = "unit", source = "zproposalItem.UNIT")
    @Mapping(target = "grquantity", source = "zproposalItem.GRQUANTITY")

    @Mapping(target = "grquantityUnit", source = "zproposalItem.GRQUANTITY_UNIT")
    @Mapping(target = "difExists", source = "zproposalItem.DIF_EXISTS")
    @Mapping(target = "excExists", source = "zproposalItem.EXC_EXISTS")
    @Mapping(target = "statval", source = "zproposalItem.STATVAL")
    @Mapping(target = "delivtst", source = "zproposalItem.DELIVTST")
    @Mapping(target = "plntst", source = "zproposalItem.PLNTST")
    @Mapping(target = "openQuantity", ignore = true)

    @Mapping(target = "averageSalesQuantity", ignore = true)
    @Mapping(target = "conversionFactor", source = "zproposalItem.CONVERSION_FACTOR")
    @Mapping(target = "timeAutoconfirmation", source = "zproposalItem.TIME_AUTOCONFIRMATION")
    @Mapping(target = "orderType", source = "zproposalItem.ORDER_TYPE")
    @Mapping(target = "maximum", ignore = true)

    @Mapping(target = "presentationStock", ignore = true)
    @Mapping(target = "purchPrice", source = "zproposalItem.PURCH_PRICE")
    @Mapping(target = "zminweight", source = "zproposalItem.ZMINWEIGHT")
    @Mapping(target = "zminbw", source = "zproposalItem.ZMINBW")
    @Mapping(target = "budget", source = "zproposalItem.BUDGET")
    @Mapping(target = "norder", ignore = true)
    @Mapping(target = "ndlvry", ignore = true)
    @Mapping(target = "dtInsert", ignore = true)
    public abstract OrderProposal toOrder(EDI_DC40 ediDc40, ZPROPOSAL_ITEM zproposalItem);
}
