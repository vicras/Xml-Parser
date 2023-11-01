package ru.x5.migration.dao.doc;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.x5.migration.dao.BaseDao;
import ru.x5.migration.domain.doc.DocDetail;

import java.util.List;

@Component
public class DocDetailDao extends BaseDao {
    protected DocDetailDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Transactional
    public List<DocDetail> batchUpdate(List<DocDetail> entities, int batchSize) {
        var params = entities.stream().map(this::getMapSqlParameterSource).toList();
        batchUpdate(docDetailSql(), params, batchSize);
        return entities;
    }

    private MapSqlParameterSource getMapSqlParameterSource(DocDetail evsdBuffer) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
//        parameterSource.addValue("id_sap", evsdBuffer.getIdSap()); TODO SAP id
        parameterSource.addValue("id_department", evsdBuffer.getIdDepartment());
        parameterSource.addValue("id_item", evsdBuffer.getIdItem());
        parameterSource.addValue("id_header", evsdBuffer.getIdHeader());
        parameterSource.addValue("id_parent", evsdBuffer.getIdParent());
        parameterSource.addValue("id_master", evsdBuffer.getIdMaster());
        parameterSource.addValue("id_consign", evsdBuffer.getIdConsign());
        parameterSource.addValue("id_taxscheme", evsdBuffer.getIdTaxscheme());
        parameterSource.addValue("qty", evsdBuffer.getQty());
        parameterSource.addValue("price", evsdBuffer.getPrice());
        parameterSource.addValue("overhead", evsdBuffer.getOverhead());
        parameterSource.addValue("vat", evsdBuffer.getVat());
        parameterSource.addValue("discount", evsdBuffer.getDiscount());
        parameterSource.addValue("lineno", evsdBuffer.getLineno());
        parameterSource.addValue("bewart", evsdBuffer.getBewart());
        parameterSource.addValue("dt_max_realization", evsdBuffer.getDtMaxRealization());
        return parameterSource;
    }

    private String docDetailSql() {
        return "insert into sdd.docdetail (id_department, id_item, id_header, id_parent, id_master, id_consign, id_taxscheme, qty, price,\n" +
                "                       overhead, vat, discount, lineno, bewart, dt_max_realization)\n" +
                "values (:id_department, :id_item, :id_header, :id_parent, :id_master, :id_consign, :id_taxscheme, :qty, :price,\n" +
                ":overhead, :vat, :discount, :lineno, :bewart, :dt_max_realization) " +
                "" +
                "ON CONFLICT ON CONSTRAINT docdetail_pkey DO NOTHING;"; // TODO on conflict with docdetails
    }
}

