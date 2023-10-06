package ru.x5.migration.dto.xml.inventory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.x5.migration.domain.inventory.TaskHOrdering;
import ru.x5.migration.domain.inventory.ZloInvTaskH;
import ru.x5.migration.domain.inventory.ZloInvTaskPos;
import ru.x5.migration.domain.inventory.ZloInvTaskZone;
import ru.x5.migration.dto.xml.inventory.E1WVINH;
import ru.x5.migration.dto.xml.inventory.E1WVINH.E1WVINI.E1WXX01;
import ru.x5.migration.dto.xml.inventory.EDI_DC40;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNullElse;
import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR, imports = {LocalDateTime.class, TaskHOrdering.class})
public abstract class InventoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "xblni", source = "e1WVINH.XBLNI")
    @Mapping(target = "sndprn", source = "ediDc40.SNDPRN")
    @Mapping(target = "name", source = "e1WVINH.DNAME")
    @Mapping(target = "vpdat", source = "e1WVINH.VPDAT")
    @Mapping(target = "ordng", expression = "java(TaskHOrdering.of(e1WVINH.ORDNG))")
    @Mapping(target = "keord", source = "e1WVINH.KEORD")
    @Mapping(target = "zdact", expression = "java(toLocalDate(e1WVINH.e1WVINI.get(0).ze1Wvinh.ZDACT))")
    @Mapping(target = "ztact", ignore = true)
    @Mapping(target = "zdate", expression = "java(toLocalDate(e1WVINH.e1WVINI.get(0).ze1Wvinh.ZDATE))")
    @Mapping(target = "ztime", expression = "java(toNumber(e1WVINH.e1WVINI.get(0).ze1Wvinh.ZTIME))")
    @Mapping(target = "ddate", source = "e1WVINH.DDATE")
    @Mapping(target = "dtime", source = "e1WVINH.DTIME")
    @Mapping(target = "datecreated", expression = "java(LocalDateTime.now())")
    public abstract ZloInvTaskH toZloInvTaskH(EDI_DC40 ediDc40, E1WVINH e1WVINH);

    public List<ZloInvTaskPos> toZloInvTaskPos(EDI_DC40 ediDc40, E1WVINH e1WVINH) {
        return e1WVINH.e1WVINI.stream()
                .map(e1WVINI -> {
                    var taskPos = new ZloInvTaskPos();
                    taskPos.setXblni(e1WVINH.XBLNI);
                    taskPos.setSndprn(ediDc40.SNDPRN);
                    taskPos.setArtnr(e1WVINI.ARTNR);
                    findByName(e1WVINI.E1WXX01, "RSOLLMG").ifPresent(val -> taskPos.setRsollmg(toDouble(val))); //where  <FLDNAME>RSOLLMG</FLDNAME>
                    taskPos.setErfmg(toNumber(e1WVINI.ERFMG));
                    taskPos.setErfme(e1WVINI.ERFME);
                    findByName(e1WVINI.E1WXX01, "PRICE").ifPresent(val -> taskPos.setFldval(toDouble(val)));
                    taskPos.setDatecreated(LocalDateTime.now());
                    return taskPos;
                }).toList();
    }

    public List<ZloInvTaskZone> toZloInvTaskZone(EDI_DC40 ediDc40, E1WVINH e1WVINH) {
        return e1WVINH.e1WVINI.stream()
                .map(e1WVINI -> {
                    String zonename = withNPECheck(() -> e1WVINI.ze1Wvinh.zones.zone.ZONENAME);
                    Double zoneqty = withNPECheck(() -> toDouble(e1WVINI.ze1Wvinh.zones.zone.ZONEQTY));
                    if(isNull(zonename) || isNull(zoneqty)) return null;
                    var taskPos = new ZloInvTaskZone();
                    taskPos.setXblni(e1WVINH.XBLNI);
                    taskPos.setSndprn(ediDc40.SNDPRN);
                    taskPos.setArtnr(e1WVINI.ARTNR);
                    taskPos.setZonename(zonename);
                    taskPos.setZoneqty(zoneqty);
                    taskPos.setDateCreated(LocalDateTime.now());
                    return taskPos;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    protected static LocalDate toLocalDate(String value) {
        if (Objects.isNull(value)) return null;
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    protected static Integer toNumber(String value) {
        if (Objects.isNull(value)) return null;
        return Integer.parseInt(value);
    }

    protected Double toDouble(String val) {
        if (Objects.isNull(val)) return null;
        return Double.parseDouble(val);
    }

    protected Optional<String> findByName(List<E1WVINH.E1WVINI.E1WXX01> entities, String fldname) {
        return entities.stream()
                .filter(x01 -> fldname.equalsIgnoreCase(x01.FLDNAME))
                .map(E1WXX01::getFLDVAL)
                .findFirst();
    }

    protected <T> T withNPECheck(Supplier<T> supplierToExecute) {
        try {
            return supplierToExecute.get();
        } catch (NullPointerException ex) {
            return null;
        }
    }
}