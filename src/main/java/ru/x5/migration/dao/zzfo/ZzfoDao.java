package ru.x5.migration.dao.zzfo;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.x5.migration.dao.BaseDao;
import ru.x5.migration.domain.zzfo.OrderProposal;

import java.util.List;

@Component
public class ZzfoDao extends BaseDao {

    protected ZzfoDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Transactional
    public List<OrderProposal> batchUpdate(List<OrderProposal> entities, int batchSize) {
        var params = entities.stream().map(this::getMapSqlParameterSource).toList();
        batchUpdate(order_proposalSQL(), params, batchSize);
        return entities;
    }

    private MapSqlParameterSource getMapSqlParameterSource(OrderProposal ordProp) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("ordno", ordProp.getOrdno());
        parameterSource.addValue("itmno", ordProp.getItmno());
        parameterSource.addValue("locnoto", ordProp.getLocnoto());
        parameterSource.addValue("matnr", ordProp.getMatnr());
        parameterSource.addValue("locnofr", ordProp.getLocnofr());
        parameterSource.addValue("slsnr", ordProp.getSlsnr());
        parameterSource.addValue("maktx", ordProp.getMaktx());
        parameterSource.addValue("matkl", ordProp.getMatkl());
        parameterSource.addValue("quantity", ordProp.getQuantity());
        parameterSource.addValue("unit", ordProp.getUnit());
        parameterSource.addValue("grquantity", ordProp.getGrquantity());
        parameterSource.addValue("grquantity_unit", ordProp.getGrquantityUnit());
        parameterSource.addValue("dif_exists", ordProp.getDifExists());
        parameterSource.addValue("exc_exists", ordProp.getExcExists());
        parameterSource.addValue("statval", ordProp.getStatval());
        parameterSource.addValue("delivtst", ordProp.getDelivtst());
        parameterSource.addValue("plntst", ordProp.getPlntst());
        parameterSource.addValue("open_quantity", ordProp.getOpenQuantity());
        parameterSource.addValue("average_sales_quantity", ordProp.getAverageSalesQuantity());
        parameterSource.addValue("conversion_factor", ordProp.getConversionFactor());
        parameterSource.addValue("time_autoconfirmation", ordProp.getTimeAutoconfirmation());
        parameterSource.addValue("order_type", ordProp.getOrderType());
        parameterSource.addValue("maximum", ordProp.getMaximum());
        parameterSource.addValue("presentation_stock", ordProp.getPresentationStock());
        parameterSource.addValue("purch_price", ordProp.getPurchPrice());
        parameterSource.addValue("zminweight", ordProp.getZminweight());
        parameterSource.addValue("zminbw", ordProp.getZminbw());
        parameterSource.addValue("budget", ordProp.getBudget());
        parameterSource.addValue("norder", ordProp.getNorder());
        parameterSource.addValue("ndlvry", ordProp.getNdlvry());
        parameterSource.addValue("dt_insert", ordProp.getDtInsert());
        return parameterSource;
    }

    private String order_proposalSQL() {
        return "insert into sdd.order_proposal (ordno, itmno, locnoto, matnr, locnofr, slsnr, maktx, matkl, quantity, unit, grquantity,\n" +
                "                            grquantity_unit, dif_exists, exc_exists, statval, delivtst, plntst, open_quantity,\n" +
                "                            average_sales_quantity, conversion_factor, time_autoconfirmation, order_type, maximum,\n" +
                "                            presentation_stock, purch_price, zminweight, zminbw, budget, norder, ndlvry, dt_insert)\n" +
                "values (:ordno, :itmno, :locnoto, :matnr, :locnofr, :slsnr, :maktx, :matkl, :quantity, :unit, :grquantity, " +
                ":grquantity_unit, :dif_exists, :exc_exists, :statval, :delivtst, :plntst, :open_quantity, :average_sales_quantity, " +
                ":conversion_factor, :time_autoconfirmation, :order_type, :maximum, :presentation_stock, :purch_price, :zminweight, " +
                ":zminbw, :budget, :norder, :ndlvry, :dt_insert)";
    }
}
