package com.example.wppl.dao;

import com.example.wppl.domain.E1WPA01;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class E1WPA01_Dao {

    private final JdbcTemplate jdbcTemplate;

    public E1WPA01_Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String getMultiInsertSql(){
      return "insert into e1wpa01 (filiale, aendkennz, aktivdatum, aenddatum, hauptean, artikelnr, " +
              "posme, e1wpa02_warengr, e1wpa02_verpgew, e1wpa02_raberlaubt, e1wpa02_prdruck, e1wpa02_artikanz, e1wpa02_mhdhb, e1wpa03_qualarttxt, e1wpa03_sprascode, e1wpa03_text, e1wpa03_lfdnr)\n" +
              "values  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    }
    @Transactional
    public void saveAll(List<E1WPA01> entities) {
        jdbcTemplate.batchUpdate(getMultiInsertSql(),
                entities,
                100,
                (PreparedStatement ps, E1WPA01 product) -> {
                    ps.setString(1, product.FILIALE);
                    ps.setString(2, product.AENDKENNZ);
                    ps.setString(3, product.AKTIVDATUM);
                    ps.setString(4, product.AENDDATUM);
                    ps.setString(5, product.HAUPTEAN);
                    ps.setString(6, product.ARTIKELNR);
                    ps.setString(7, product.POSME);
                    ps.setString(8, product.e1WPA02.WARENGR);
                    ps.setString(9, product.e1WPA02.VERPGEW);
                    ps.setString(10, product.e1WPA02.RABERLAUBT);
                    ps.setString(11, product.e1WPA02.PRDRUCK);
                    ps.setString(12, product.e1WPA02.ARTIKANZ);
                    ps.setInt(13, Integer.parseInt(product.e1WPA02.MHDHB));
                    ps.setString(14, product.e1WPA03.QUALARTTXT);
                    ps.setString(15, product.e1WPA03.SPRASCODE);
                    ps.setString(16, product.e1WPA03.TEXT);
                    ps.setString(17, product.e1WPA03.LFDNR);
                });
    }
}
