package ru.x5.migration.dao.inventory;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public abstract class BaseDao {

    protected final NamedParameterJdbcTemplate jdbcTemplate;

    protected BaseDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected void batchUpdate(String sql, List<MapSqlParameterSource> queryParams, int batchSize) {
        for (int fromIndex = 0; fromIndex < queryParams.size(); fromIndex += batchSize) {
            var toIndex = Math.min(fromIndex + batchSize, queryParams.size());
            var batchParams = queryParams.subList(fromIndex, toIndex);
            jdbcTemplate.batchUpdate(sql, batchParams.toArray(MapSqlParameterSource[]::new));
        }
    }
}
