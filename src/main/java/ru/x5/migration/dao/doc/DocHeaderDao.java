package ru.x5.migration.dao.doc;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.x5.migration.dao.BaseDao;
import ru.x5.migration.domain.doc.DocHeader;

import java.util.List;
import java.util.Objects;

@Component
public class DocHeaderDao extends BaseDao {
    protected DocHeaderDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Transactional
    public List<DocHeader> batchUpdate(List<DocHeader> entities, int batchSize) {
        var params = entities.stream().map(this::getMapSqlParameterSource).toList();
        batchUpdate(docHeaderSql(), params, batchSize);
        return entities;
    }

    private MapSqlParameterSource getMapSqlParameterSource(DocHeader evsdBuffer) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
//        parameterSource.addValue("id_sap", evsdBuffer.getIdSap()); TODO sap id
        parameterSource.addValue("id_department", evsdBuffer.getIdDepartment());
        parameterSource.addValue("id_header", evsdBuffer.getIdHeader());
        parameterSource.addValue("id_doctype", evsdBuffer.getIdDoctype());
        parameterSource.addValue("id_client", evsdBuffer.getIdClient());
        parameterSource.addValue("status", evsdBuffer.getStatus().getDbName());
        Object currency = Objects.nonNull(evsdBuffer.getCurrency()) ? evsdBuffer.getCurrency().getDbName() : null;
        parameterSource.addValue("currency", currency);
        parameterSource.addValue("dt_created", evsdBuffer.getDtCreated());
        parameterSource.addValue("dt_payed", evsdBuffer.getDtPayed());
        parameterSource.addValue("summ", evsdBuffer.getSumm());
        parameterSource.addValue("overhead", evsdBuffer.getOverHead());
        parameterSource.addValue("docnumber", evsdBuffer.getDocNumber());
        parameterSource.addValue("note", evsdBuffer.getNote());
        parameterSource.addValue("is_consign", evsdBuffer.getConsign().getDbName());
        parameterSource.addValue("is_vat", evsdBuffer.getVat().getDbName());
        parameterSource.addValue("memo", evsdBuffer.getMemo());
        parameterSource.addValue("vat20", evsdBuffer.getVat20());
        parameterSource.addValue("vat10", evsdBuffer.getVat10());
        parameterSource.addValue("id_reference", evsdBuffer.getIdReference());
        parameterSource.addValue("id_cashtype", evsdBuffer.getIdCashtype());
        parameterSource.addValue("dt_update", evsdBuffer.getDtUpdate());
        parameterSource.addValue("is_shipped", evsdBuffer.getIsShipped());
        parameterSource.addValue("is_duty", evsdBuffer.getIsDuty());
        parameterSource.addValue("ax_note", evsdBuffer.getAxNote());
        parameterSource.addValue("id_ax_header", evsdBuffer.getIdAxHeader());
        parameterSource.addValue("is_paid", evsdBuffer.getIsPaid());
        parameterSource.addValue("qix", evsdBuffer.getQix());
        parameterSource.addValue("vat18", evsdBuffer.getVat18());
        parameterSource.addValue("ord_id_header", evsdBuffer.getOrdIdHeader());
        parameterSource.addValue("dt_detail_start", evsdBuffer.getDtDetailStart());
        parameterSource.addValue("dt_detail_finish", evsdBuffer.getDtDetailFinish());
        parameterSource.addValue("export_sap", evsdBuffer.getExportSap());
        parameterSource.addValue("id_sap_header", evsdBuffer.getIdSapHeader());
        parameterSource.addValue("sap_note", evsdBuffer.getSapNote());
        parameterSource.addValue("id_sap_doctype", evsdBuffer.getIdSapDoctype());
        return parameterSource;
    }

    private String docHeaderSql() {
        return "insert into sdd.docheader (id_department, id_header, id_doctype, id_client, status, currency,\n" +
                "                           dt_created, dt_payed, summ, overhead, docnumber, note, is_consign, is_vat, memo, vat20,\n" +
                "                           vat10, id_reference, id_cashtype, dt_update, is_shipped, is_duty, ax_note,\n" +
                "                           id_ax_header, is_paid, qix, vat18, ord_id_header, dt_detail_start, dt_detail_finish,\n" +
                "                           export_sap, id_sap_header, sap_note, id_sap_doctype)\n" +
                "        values (:id_department, :id_header, :id_doctype, :id_client, :status, :currency,\n" +
                "                :dt_created, :dt_payed, :summ, :overhead, :docnumber, :note, :is_consign, :is_vat, :memo, :vat20,\n" +
                "                :vat10, :id_reference, :id_cashtype, :dt_update, :is_shipped, :is_duty, :ax_note,\n" +
                "                :id_ax_header, :is_paid, :qix, :vat18, :ord_id_header, :dt_detail_start, :dt_detail_finish,\n" +
                "                :export_sap, :id_sap_header, :sap_note, :id_sap_doctype);";
    }
}

