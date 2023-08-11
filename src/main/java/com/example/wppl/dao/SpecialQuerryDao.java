package com.example.wppl.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpecialQuerryDao {

    private final JdbcTemplate jdbcTemplate;

    public SpecialQuerryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> findAllDocnum() {
        String sql = "select e.docnum from e1wpa01 join public.edi_dc40 e on e1wpa01.filiale = e.rcvprn where e1wpa01.e1wpa02_mhdhb < 50";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getString("docnum")
        );
    }
}
