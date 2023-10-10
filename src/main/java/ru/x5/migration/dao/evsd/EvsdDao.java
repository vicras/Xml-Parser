package ru.x5.migration.dao.evsd;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.x5.migration.dao.BaseDao;
import ru.x5.migration.domain.evsd.EvsdBuffer;

import java.util.List;

@Component
public class EvsdDao extends BaseDao {

    protected EvsdDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Transactional
    public List<EvsdBuffer> batchUpdate(List<EvsdBuffer> entities, int batchSize) {
        var params = entities.stream().map(this::getMapSqlParameterSource).toList();
        batchUpdate(zlo_inv_task_hSQL(), params, batchSize);
        return entities;
    }

    private MapSqlParameterSource getMapSqlParameterSource(EvsdBuffer evsdBuffer) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("PLANTCODE", evsdBuffer.getPlantCode());
        parameterSource.addValue("VETDOCUUID", evsdBuffer.getVetDocUUID());
        parameterSource.addValue("PRODUCTCODE", evsdBuffer.getProductCode());
        parameterSource.addValue("PRODUCTITEMGTIN", evsdBuffer.getProductItemGTIN());
        parameterSource.addValue("PRODUCTNAME", evsdBuffer.getProductName());
        parameterSource.addValue("WAYBILLNO", evsdBuffer.getWayBillNo());
        parameterSource.addValue("WAYBILLDATE", evsdBuffer.getWayBillDate());
        parameterSource.addValue("DT_INSERT", evsdBuffer.getDtInsert());
        return parameterSource;
    }

    private String zlo_inv_task_hSQL() {
        return "insert into sap.EVSD_BUFFER (PLANTCODE, \"VETDOCUUID\", \"PRODUCTCODE\", \"PRODUCTITEMGTIN\", \"PRODUCTNAME\", \"WAYBILLNO\", \"WAYBILLDATE\",\n" +
                "    \"DT_INSERT\")\n" +
                "    values (:PLANTCODE, :VETDOCUUID, :PRODUCTCODE, :PRODUCTITEMGTIN, :PRODUCTNAME, :WAYBILLNO, :WAYBILLDATE, :DT_INSERT);";
    }
}
