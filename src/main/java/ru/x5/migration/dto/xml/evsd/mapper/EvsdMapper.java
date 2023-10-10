package ru.x5.migration.dto.xml.evsd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.x5.migration.domain.evsd.EvsdBuffer;
import ru.x5.migration.domain.inventory.TaskHOrdering;
import ru.x5.migration.dto.xml.evsd.Body;

import java.time.LocalDateTime;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR, imports = {LocalDateTime.class, TaskHOrdering.class})
public abstract class EvsdMapper {

    @Mapping(target = "plantCode", source = "body.PlantCode")
    @Mapping(target = "vetDocUUID", source = "body.eVetDoc.VetDocUUID")
    @Mapping(target = "productCode", source = "body.ProductCode")
    @Mapping(target = "productItemGTIN", source = "body.ProductItemGTIN")
    @Mapping(target = "productName", source = "body.ProductName")
    @Mapping(target = "wayBillNo", source = "body.WayBillNo")
    @Mapping(target = "wayBillDate", source = "body.WayBillDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "dtInsert", expression = "java(LocalDateTime.now())")
    public abstract EvsdBuffer toEvsdBuffer(Body body);
}
