package ru.x5.migration.dao.inventory;

import ru.x5.migration.domain.inventory.ZloInvTaskZone;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ZloInvTaskZoneDao extends BaseDao {
    protected ZloInvTaskZoneDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void batchUpdate(List<ZloInvTaskZone> entities, int batchSize) {
        var params = entities.stream().map(this::getMapSqlParameterSource).toList();
        batchUpdate(zlo_inv_task_zoneSQL(), params, batchSize);
    }

    private MapSqlParameterSource getMapSqlParameterSource(ZloInvTaskZone taskZone) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("parentid",taskZone.getParentid());
        parameterSource.addValue("xblni",taskZone.getXblni());
        parameterSource.addValue("sndprn",taskZone.getSndprn());
        parameterSource.addValue("artnr",taskZone.getArtnr());
        parameterSource.addValue("zonename",taskZone.getZonename());
        parameterSource.addValue("zoneqty",taskZone.getZoneqty());
        parameterSource.addValue("datecreated",taskZone.getDateCreated());
        return parameterSource;
    }

    private String zlo_inv_task_zoneSQL() {
        return "insert into zlo_inv_task_zone (parentid, xblni, sndprn, artnr, zonename, zoneqty, datecreated)\n" +
                "values (:parentid, :xblni, :sndprn, :artnr, :zonename, :zoneqty, :datecreated);";
    }
}
