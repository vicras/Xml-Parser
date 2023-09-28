package ru.x5.migration.dao.inventory;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.x5.migration.domain.inventory.ZloInvTaskH;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Component
public class ZloInvTaskHDao extends BaseDao {
    protected ZloInvTaskHDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public List<ZloInvTaskH> batchUpdate(List<ZloInvTaskH> entities, int batchSize) {
        enhanceWithIds(entities);
        var params = entities.stream().map(this::getMapSqlParameterSource).toList();
        batchUpdate(zlo_inv_task_hSQL(), params, batchSize);
        return entities;
    }

    public void enhanceWithIds(List<ZloInvTaskH> entities) {
        var nextIds = getSeveralIds(entities.size());
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).setId(nextIds.get(i));
        }
    }

    private List<BigInteger> getSeveralIds(int amounts) {
        String sql = nextSeqRequest();
        return jdbcTemplate.queryForList(sql, Map.of("amounts", amounts), BigInteger.class);
    }

    private MapSqlParameterSource getMapSqlParameterSource(ZloInvTaskH taskH) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", taskH.getId());
        parameterSource.addValue("xblni", taskH.getXblni());
        parameterSource.addValue("sndprn", taskH.getSndprn());
        parameterSource.addValue("name", taskH.getName());
        parameterSource.addValue("vpdat", taskH.getVpdat());
        parameterSource.addValue("ordng", taskH.getOrdng().getOrder());
        parameterSource.addValue("keord", taskH.getKeord());
        parameterSource.addValue("zdact", taskH.getZdact());
        parameterSource.addValue("ztact", taskH.getZtact());
        parameterSource.addValue("zdate", taskH.getZdate());
        parameterSource.addValue("ztime", taskH.getZtime());
        parameterSource.addValue("ddate", taskH.getDdate());
        parameterSource.addValue("dtime", taskH.getDtime());
        parameterSource.addValue("datecreated", taskH.getDatecreated());
        return parameterSource;
    }

    private String zlo_inv_task_hSQL() {
        return "insert into zlo_inv_task_h (id, xblni, sndprn, name, vpdat, ordng, keord, zdact, ztact, zdate, ztime, ddate, dtime, datecreated)\n" +
                "values (:id, :xblni, :sndprn, :name, :vpdat, :ordng, :keord, :zdact, :ztact, :zdate, :ztime, :ddate, :dtime, :datecreated);";
    }

    private String nextSeqRequest() {
        return "SELECT NEXTVAL('zlo_inv_task_h_seq') FROM generate_series(1,:amounts);";
    }

}
