package ru.x5.migration.dto.xml.doc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.x5.migration.domain.doc.*;
import ru.x5.migration.domain.inventory.TaskHOrdering;
import ru.x5.migration.dto.xml.doc.Details;
import ru.x5.migration.dto.xml.doc.Header;

import java.time.LocalDateTime;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR,
        imports = {LocalDateTime.class, TaskHOrdering.class, HeaderStatus.class,
                HeaderCurrency.class, HeaderConsign.class, HeaderVat.class
        }
)
public abstract class DocMapper {

    @Mapping(target = "idSap", source = "idSap")
    @Mapping(target = "idDepartment", source = "header.ID_DEPARTMENT")
    @Mapping(target = "idHeader", source = "header.ID_HEADER")
    @Mapping(target = "idDoctype", source = "header.ID_DOCTYPE")
    @Mapping(target = "idClient", source = "header.ID_CLIENT")
    @Mapping(target = "status", source = "header.STATUS", defaultExpression = "java(HeaderStatus.OPEN)")
    @Mapping(target = "currency", source = "header.CURRENCY", qualifiedByName = "toCurrency")
    @Mapping(target = "dtCreated", source = "header.DT_CREATED", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "dtPayed", source = "header.DT_PAYED")
    @Mapping(target = "summ", source = "header.SUMM")
    @Mapping(target = "overHead", source = "header.OVERHEAD")
    @Mapping(target = "docNumber", source = "header.DOCNUMBER")
    @Mapping(target = "note", source = "header.NOTE") // TODO NOTE mapping logic
    // CASE WHEN HEADERS.NOTE IS NULL AND HEADERS. r(64) ID_DOCTYPE = 46 THEN VNOTE ELSE HEADERS.NOTE
    @Mapping(target = "consign", source = "header.IS_CONSIGN", defaultExpression = "java(HeaderConsign.OWNERSHIP)", qualifiedByName = "toConsign")
    @Mapping(target = "vat", source = "header.IS_VAT", defaultExpression = "java(HeaderVat.EXCLUDE)", qualifiedByName = "toVat")
    @Mapping(target = "memo", source = "header.MEMO")
    @Mapping(target = "vat20", source = "header.VAT20")
    @Mapping(target = "vat10", source = "header.VAT10")
    @Mapping(target = "idReference", source = "header.ID_REFERENCE")
    @Mapping(target = "idCashtype", source = "header.ID_CASHTYPE")
    @Mapping(target = "dtUpdate", source = "header.DT_UPDATE")
    @Mapping(target = "isShipped", source = "header.IS_SHIPPED", defaultValue = "1")
    @Mapping(target = "isDuty", source = "header.IS_DUTY", defaultValue = "1")
    @Mapping(target = "axNote", source = "header.AX_NOTE")
    @Mapping(target = "idAxHeader", source = "header.ID_AX_HEADER")
    @Mapping(target = "isPaid", source = "header.IS_PAID")
    @Mapping(target = "qix", ignore = true)
    @Mapping(target = "vat18", ignore = true)
    @Mapping(target = "ordIdHeader", ignore = true)
    @Mapping(target = "dtDetailStart", ignore = true)
    @Mapping(target = "dtDetailFinish", ignore = true)
    @Mapping(target = "exportSap", ignore = true)
    @Mapping(target = "idSapHeader", ignore = true)
    @Mapping(target = "sapNote", ignore = true)
    @Mapping(target = "idSapDoctype", source = "header.ID_SAP_DOCTYPE")
    public abstract DocHeader toDocHeader(Header header, String idSap);

    @Mapping(target = "idSap", source = "idSap")
    @Mapping(target = "idDepartment", source = "header.ID_DEPARTMENT")
    @Mapping(target = "idHeader", source = "header.ID_HEADER")
    @Mapping(target = "idItem", source = "detail.ID_ITEM")
    @Mapping(target = "idParent", source = "detail.ID_PARENT", defaultValue = "0")
    @Mapping(target = "idMaster", source = "detail.ID_MASTER", defaultValue = "0")
    @Mapping(target = "idConsign", source = "detail.ID_CONSIGN", defaultValue = "0")
    @Mapping(target = "idTaxscheme", source = "detail.ID_TAXSCHEME")
    @Mapping(target = "qty", source = "detail.QTY")
    @Mapping(target = "price", source = "detail.PRICE")
    @Mapping(target = "overhead", source = "detail.OVERHEAD")
    @Mapping(target = "vat", source = "detail.VAT")
    @Mapping(target = "discount", source = "detail.DISCOUNT")
    @Mapping(target = "lineno", source = "detail.LINENO")
    @Mapping(target = "bewart", source = "detail.BEWART")
    @Mapping(target = "dtMaxRealization", ignore = true)
    public abstract DocDetail toDocDetail(Details.Detail detail, Header header, String idSap);

    public static HeaderStatus toStatus(String name) {
        return HeaderStatus.of(name);
    }

    @Named("toCurrency")
    public static HeaderCurrency toCurrency(Integer name) {
        return HeaderCurrency.of(name);
    }

    @Named("toVat")
    public static HeaderVat toVat(Integer name) {
        return HeaderVat.of(name);
    }

    @Named("toConsign")
    public static HeaderConsign toConsign(Integer name) {
        return HeaderConsign.of(name);
    }
}
