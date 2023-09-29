package ru.x5.migration.dao.inventory;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.x5.migration.dao.BaseDao;
import ru.x5.migration.domain.inventory.ZloInvTaskPos;

import java.util.List;

@Component
public class ZloInvTaskPosDao extends BaseDao {
    protected ZloInvTaskPosDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Transactional
    public void batchUpdate(List<ZloInvTaskPos> entities, int batchSize) {
        var params = entities.stream().map(this::getMapSqlParameterSource).toList();
        batchUpdate(zlo_inv_task_posSQL(), params, batchSize);
    }

    private MapSqlParameterSource getMapSqlParameterSource(ZloInvTaskPos taskPos) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("parentid", taskPos.getParentid());
        parameterSource.addValue("xblni", taskPos.getXblni());
        parameterSource.addValue("sndprn", taskPos.getSndprn());
        parameterSource.addValue("artnr", taskPos.getArtnr());
        parameterSource.addValue("rsollmg", taskPos.getRsollmg());
        parameterSource.addValue("erfmg", taskPos.getErfmg());
        parameterSource.addValue("erfme", taskPos.getErfme());
        parameterSource.addValue("fldval", taskPos.getFldval());
        parameterSource.addValue("datecreated", taskPos.getDatecreated());
        return parameterSource;
    }

    private String zlo_inv_task_posSQL() {
        return "insert into zlo_inv_task_pos (parentid, xblni, sndprn, artnr, rsollmg, erfmg, erfme, fldval, datecreated)\n" +
                "values (:parentid, :xblni, :sndprn, :artnr, :rsollmg, :erfmg, :erfme, :fldval, :datecreated);";
    }
}
