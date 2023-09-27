package com.example.wppl.dao.inventory;

import com.example.wppl.domain.inventory.E1WVINH;
import com.example.wppl.domain.inventory.E1WVINH.E1WVINI.E1WXX01;
import com.example.wppl.domain.inventory.EDI_DC40;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class InventoryDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;


    public InventoryDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void saveInventory(EDI_DC40 ediDc40, E1WVINH e1WVINH) {
        var mainId = (getRandom());
        zlo_inv_task_hInsert(ediDc40, e1WVINH, mainId);
        zlo_inv_task_posInsert(ediDc40, e1WVINH, mainId);
        zlo_inv_task_zoneInsert(ediDc40, e1WVINH, mainId);
    }

    private void zlo_inv_task_hInsert(EDI_DC40 ediDc40, E1WVINH e1WVINH, BigInteger mainId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", mainId);
        parameterSource.addValue("xblni", e1WVINH.XBLNI);
        parameterSource.addValue("sndprn", ediDc40.SNDPRN);
        parameterSource.addValue("name", e1WVINH.DNAME);
        parameterSource.addValue("vpdat", getTimestamp(e1WVINH.VPDAT));
        parameterSource.addValue("ordng", mapOrdering(e1WVINH.ORDNG));
        parameterSource.addValue("keord", getNumber(e1WVINH.KEORD));
        parameterSource.addValue("zdact", getTimestamp(e1WVINH.E1WVINI.get(0).ZE1WVINH.ZDACT));
        parameterSource.addValue("ztact", null);
        parameterSource.addValue("zdate", getTimestamp(e1WVINH.E1WVINI.get(0).ZE1WVINH.ZDATE));
        parameterSource.addValue("ztime", getNumber(e1WVINH.E1WVINI.get(0).ZE1WVINH.ZTIME));
        parameterSource.addValue("ddate", getTimestamp(e1WVINH.DDATE));
        parameterSource.addValue("dtime", getNumber(e1WVINH.DTIME));
        parameterSource.addValue("datecreated", LocalDateTime.now());

        var parameters = new MapSqlParameterSource[]{parameterSource};
        jdbcTemplate.batchUpdate(zlo_inv_task_hSQL(), parameters);
    }

    private String zlo_inv_task_hSQL() {
        return "insert into zlo_inv_task_h (id, xblni, sndprn, name, vpdat, ordng, keord, zdact, ztact, zdate, ztime, ddate, dtime, datecreated)\n" +
                "values (:id, :xblni, :sndprn, :name, :vpdat, :ordng, :keord, :zdact, :ztact, :zdate, :ztime, :ddate, :dtime, :datecreated);";
    }

    private void zlo_inv_task_posInsert(EDI_DC40 ediDc40, E1WVINH e1WVINH, BigInteger mainId) {

        var parameters = e1WVINH.E1WVINI.stream()
                .map(e1WVINI -> {
                    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                    parameterSource.addValue("parentid", mainId);
                    parameterSource.addValue("xblni", e1WVINH.XBLNI);
                    parameterSource.addValue("sndprn", ediDc40.SNDPRN);
                    parameterSource.addValue("artnr", e1WVINI.ARTNR);
                    findByName(e1WVINI.E1WXX01, "RSOLLMG").ifPresent(val -> parameterSource.addValue("rsollmg", getDouble(val))); //where  <FLDNAME>RSOLLMG</FLDNAME>
                    parameterSource.addValue("erfmg", getNumber(e1WVINI.ERFMG));
                    parameterSource.addValue("erfme", e1WVINI.ERFME);
                    findByName(e1WVINI.E1WXX01, "PRICE").ifPresent(val -> parameterSource.addValue("fldval", getDouble(val)));//where  <FLDNAME>PRICE</FLDNAME>
                    parameterSource.addValue("datecreated", LocalDateTime.now());
                    return parameterSource;
                }).toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(zlo_inv_task_posSQL(), parameters);
    }

    private Optional<String> findByName(List<E1WXX01> entities, String fldname) {
        return entities.stream()
                .filter(x01 -> fldname.equalsIgnoreCase(x01.FLDNAME))
                .map(E1WXX01::getFLDVAL)
                .findFirst();
    }

    private String zlo_inv_task_posSQL() {
        return "insert into zlo_inv_task_pos (parentid, xblni, sndprn, artnr, rsollmg, erfmg, erfme, fldval, datecreated)\n" +
                "values (:parentid, :xblni, :sndprn, :artnr, :rsollmg, :erfmg, :erfme, :fldval, :datecreated);";
    }


    private void zlo_inv_task_zoneInsert(EDI_DC40 ediDc40, E1WVINH e1WVINH, BigInteger mainId) {
        var parameters = e1WVINH.E1WVINI.stream()
                .map(e1WVINI -> {
                    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                    parameterSource.addValue("parentid", mainId); // what id use
                    parameterSource.addValue("xblni", e1WVINH.XBLNI);
                    parameterSource.addValue("sndprn", ediDc40.SNDPRN);
                    parameterSource.addValue("artnr", e1WVINI.ARTNR);
                    parameterSource.addValue("zonename", withNPECheck(() -> e1WVINI.ZE1WVINH.ZONES.ZONE.ZONENAME));
                    parameterSource.addValue("zoneqty", withNPECheck(() -> getDouble(e1WVINI.ZE1WVINH.ZONES.ZONE.ZONEQTY)));
                    parameterSource.addValue("datecreated", LocalDateTime.now());
                    return parameterSource;
                }).toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(zlo_inv_task_zoneSQL(), parameters);
    }

    private String zlo_inv_task_zoneSQL() {
        return "insert into zlo_inv_task_zone (parentid, xblni, sndprn, artnr, zonename, zoneqty, datecreated)\n" +
                "values (:parentid, :xblni, :sndprn, :artnr, :zonename, :zoneqty, :datecreated);";
    }

    private static LocalDate getTimestamp(String value) {
        if (Objects.isNull(value)) return null;
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private static Integer getNumber(String value) {
        if (Objects.isNull(value)) return null;
        return Integer.parseInt(value);
    }

    private static BigInteger getRandom() {
        return new BigInteger(RandomStringUtils.random(20, false, true));
    }

    private Double getDouble(String val) {
        if (Objects.isNull(val)) return null;
        return Double.parseDouble(val);
    }

    private static Map<String, Integer> ORDERING_MAP = Map.of(
            "OPEN", 0,
            "STARTED", 1,
            "MANUALY_CLOSED", 2,
            "AUTOCLOSED", 3,
            "BOOK", 4,
            "REOPEN", 5,
            "VERIFIED", 6,
            "CONFIRMED", 7,
            "CONTROL_REQUIRED", 8
    );

    private int mapOrdering(String text) {
        return ORDERING_MAP.getOrDefault(text, 9);
    }

    interface ExceptionalSupplier<T> {
        T supply() throws NullPointerException;
    }

    private <T> T withNPECheck(ExceptionalSupplier<T> supplierToExecute) {
        try {
            return supplierToExecute.supply();
        } catch (NullPointerException ex) {
            return null;
        }
    }
}
