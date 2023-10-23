package ru.x5.migration.dao.bill;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.x5.migration.dao.BaseDao;
import ru.x5.migration.domain.bill.Bill;

import java.util.List;

@Component
public class BillDao extends BaseDao {

    protected BillDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Transactional
    public List<Bill> batchUpdate(List<Bill> entities, int batchSize) {
        var params = entities.stream().map(this::getMapSqlParameterSource).toList();
        batchUpdate(checkSql(), params, batchSize);
        return entities;
    }

    private MapSqlParameterSource getMapSqlParameterSource(Bill bill) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("plant", bill.getPlant());
        parameterSource.addValue("distrChan", bill.getDistrChan());
        parameterSource.addValue("rpaWid", bill.getRpaWid());
        parameterSource.addValue("zchhId", bill.getZchhId());
        parameterSource.addValue("zttype", bill.getZttype());
        parameterSource.addValue("rpaSat", bill.getRpaSat());
        parameterSource.addValue("businessdaydate", bill.getBusinessdaydate());
        parameterSource.addValue("zchftim", bill.getZchftim());
        parameterSource.addValue("storno", bill.getStorno());
        parameterSource.addValue("zcustnum", bill.getZcustnum());
        parameterSource.addValue("zpostyp", bill.getZpostyp());
        parameterSource.addValue("material", bill.getMaterial());
        parameterSource.addValue("zetyp", bill.getZetyp());
        parameterSource.addValue("zeanupc", bill.getZeanupc());
        parameterSource.addValue("rpaRlq", bill.getRpaRlq());
        parameterSource.addValue("salesUnit", bill.getSalesUnit());
        parameterSource.addValue("baseQty", bill.getBaseQty());
        parameterSource.addValue("baseUom", bill.getBaseUom());
        parameterSource.addValue("rpaSatPos", bill.getRpaSat());
        parameterSource.addValue("normalamt", bill.getNormalamt());
        parameterSource.addValue("zprice", bill.getZprice());
        parameterSource.addValue("zrtaction", bill.getZrtaction());
        parameterSource.addValue("zpayTyp", bill.getZpayTyp());
        parameterSource.addValue("zdistype", bill.getZdistype());
        parameterSource.addValue("zdiscnum", bill.getZdiscnum());
        parameterSource.addValue("rpaDid", bill.getRpaDid());
        parameterSource.addValue("zdiscount", bill.getZdiscnum());
        parameterSource.addValue("zdiscamnt", bill.getZdiscamnt());
        parameterSource.addValue("zpriceold", bill.getZpriceold());
        parameterSource.addValue("zscanType", bill.getZscanType());
        parameterSource.addValue("datamatrix", bill.getDatamatrix());
        parameterSource.addValue("dateosg", bill.getDateosg());
        return parameterSource;
    }

    private String checkSql() {
        return "insert into cash.chek (plant, distr_chan, rpa_wid, zchh_id, zttype, rpa_sat, zchftim, storno, zcustnum, zpostyp,\n" +
                "                       material, zetyp, zeanupc, rpa_rlq, sales_unit, base_qty, base_uom, rpa_sat_pos, normalamt,\n" +
                "                       zprice, zrtaction, zpay_typ, zdistype, zdiscnum, rpa_did, zdiscount, zdiscamnt, zpriceold,\n" +
                "                       zscan_type, datamatrix, dateosg, businessdaydate)\n" +
                "values (:plant, :distrChan, :rpaWid, :zchhId, :zttype, :rpaSat, :zchftim, :storno, :zcustnum, :zpostyp, \n" +
                ":material, :zetyp, :zeanupc, :rpaRlq, :salesUnit, :baseQty, :baseUom, :rpaSatPos, :normalamt, :zprice, :zrtaction, :zpayTyp, \n" +
                ":zdistype, :zdiscnum, :rpaDid, :zdiscount, :zdiscamnt, :zpriceold, :zscanType, :datamatrix, :dateosg, :businessdaydate);";
    }
}
