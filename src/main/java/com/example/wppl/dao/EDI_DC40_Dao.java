package com.example.wppl.dao;

import com.example.wppl.domain.EDI_DC40;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class EDI_DC40_Dao {

    private final JdbcTemplate jdbcTemplate;

    public EDI_DC40_Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void saveAll(List<EDI_DC40> entities) {
        jdbcTemplate.batchUpdate(getMultiInsertSql(),
                entities,
                100,
                (PreparedStatement ps, EDI_DC40 product) -> {
                    ps.setString(1, product.DOCNUM);
                    ps.setString(2, product.TABNAM);
                    ps.setString(3, product.MANDT);
                    ps.setString(4, product.DOCREL);
                    ps.setString(5, product.STATUS);
                    ps.setString(6, product.DIRECT);
                    ps.setString(7, product.OUTMOD);
                    ps.setString(8, product.IDOCTYP);
                    ps.setString(9, product.MESTYP);
                    ps.setString(10, product.SNDPOR);
                    ps.setString(11, product.SNDPRT);
                    ps.setString(12, product.SNDPRN);
                    ps.setString(13, product.RCVPOR);
                    ps.setString(14, product.RCVPRT);
                    ps.setString(15, product.CREDAT);
                    ps.setString(16, product.CRETIM);
                    ps.setString(17, product.SERIAL);
                    ps.setString(18, product.RCVPRN);
                });
    }

    private String getMultiInsertSql() {
        return "insert into edi_dc40 (docnum, tabnam, mandt, docrel, status, direct, outmod, idoctyp, mestyp, " +
                "sndpor, sndprt, sndprn, rcvpor, rcvprt, credat, cretim, serial, rcvprn)\n" +
                "values  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    }
}
