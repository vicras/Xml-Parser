package ru.x5.migration.dao.markdown;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.x5.migration.dao.BaseDao;
import ru.x5.migration.domain.markdown.Markdown;

import java.util.List;

@Component
public class MarkdownDao extends BaseDao {

    protected MarkdownDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Transactional
    public List<Markdown> batchUpdate(List<Markdown> entities, int batchSize) {
        var params = entities.stream().map(this::getMapSqlParameterSource).toList();
        batchUpdate(zlo_inv_task_hSQL(), params, batchSize);
        return entities;
    }

    private MapSqlParameterSource getMapSqlParameterSource(Markdown taskH) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("werks", taskH.getWerks());
        parameterSource.addValue("createdate", taskH.getCreateDate());
        parameterSource.addValue("markdown_id", taskH.getMarkdownId());
        parameterSource.addValue("markdown_code", taskH.getMarkdownCode());
        parameterSource.addValue("dt_creation", taskH.getDtCreation());
        parameterSource.addValue("dt_price", taskH.getDtPrice());
        parameterSource.addValue("dt_change", taskH.getDtChange());
        parameterSource.addValue("plu", taskH.getPlu());
        parameterSource.addValue("price", taskH.getPrice());
        parameterSource.addValue("qty", taskH.getQty());
        parameterSource.addValue("regular_price", taskH.getRegularPrice());
        parameterSource.addValue("status", taskH.getStatus());
        parameterSource.addValue("uom", taskH.getUom());
        parameterSource.addValue("type", taskH.getType());
        parameterSource.addValue("dt_start", taskH.getDtStart());
        parameterSource.addValue("dt_end", taskH.getDtEnd());
        parameterSource.addValue("sellingprice", taskH.getSellingPrice());
        parameterSource.addValue("sap_id", taskH.getSapId());
        parameterSource.addValue("datecreated", taskH.getDateCreated());
        return parameterSource;
    }

    private String zlo_inv_task_hSQL() {
        return "insert into ctrl.xrg_markdown (werks, createdate, markdown_id, markdown_code, dt_creation, dt_price, dt_change, plu, price,\n" +
                "                              qty, regular_price, status, uom, type, dt_start, dt_end, sellingprice, sap_id, datecreated)\n" +
                "values (:werks, :createdate, :markdown_id, :markdown_code, :dt_creation, :dt_price, :dt_change, :plu, :price, :qty, " +
                ":regular_price, :status, :uom, :type, :dt_start, :dt_end, :sellingprice, :sap_id, :datecreated);";
    }
}
