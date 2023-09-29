package ru.x5.migration.dto.xml.markdown.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.x5.migration.domain.inventory.TaskHOrdering;
import ru.x5.migration.domain.markdown.Markdown;
import ru.x5.migration.dto.xml.markdown.Mt_mkd;
import ru.x5.migration.dto.xml.markdown.Row;

import java.time.LocalDateTime;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR, imports = {LocalDateTime.class, TaskHOrdering.class})
public abstract class MarkdownMapper {

    @Mapping(target = "werks", source = "mtMkd.WERKS")
    @Mapping(target = "createDate", source = "mtMkd.CREATEDATE", dateFormat = "yyyyMMddHHmmss")
    @Mapping(target = "markdownId", source = "row.MARKDOWN_ID")
    @Mapping(target = "markdownCode", source = "row.MARKDOWN_CODE")
    @Mapping(target = "dtCreation", source = "row.DT_CREATION", dateFormat = "yyyyMMddHHmmss")
    @Mapping(target = "dtPrice", source = "row.DT_START", dateFormat = "yyyyMMddHHmmss")
    @Mapping(target = "dtChange", source = "row.DT_CHANGE", dateFormat = "yyyyMMddHHmmss")
    @Mapping(target = "plu", source = "row.PLU")
    @Mapping(target = "price", source = "row.PRICE")
    @Mapping(target = "qty", source = "row.QTY")
    @Mapping(target = "regularPrice", source = "row.REGULAR_PRICE")
    @Mapping(target = "status", source = "row.STATUS")
    @Mapping(target = "uom", source = "row.UOM")
    @Mapping(target = "type", source = "row.TYPE")
    @Mapping(target = "dtStart", source = "row.DT_START", dateFormat = "yyyyMMddHHmmss")
    @Mapping(target = "dtEnd", source = "row.DT_END", dateFormat = "yyyyMMddHHmmss")
    @Mapping(target = "sellingPrice", source = "row.SELLINGPRICE")
    @Mapping(target = "sapId", source = "row.SAP_ID")
    @Mapping(target = "dateCreated", expression = "java(LocalDateTime.now())")
    public abstract Markdown toMarkdown(Mt_mkd mtMkd, Row row);
}
