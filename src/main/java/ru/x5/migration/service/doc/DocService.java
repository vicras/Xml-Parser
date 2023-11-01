package ru.x5.migration.service.doc;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.x5.migration.dao.doc.DocDetailDao;
import ru.x5.migration.dao.doc.DocHeaderDao;
import ru.x5.migration.domain.doc.DocDetail;
import ru.x5.migration.domain.doc.DocHeader;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.doc.Details;
import ru.x5.migration.dto.xml.doc.Doc;
import ru.x5.migration.dto.xml.doc.Header;
import ru.x5.migration.dto.xml.doc.Mt_doc_conc;
import ru.x5.migration.dto.xml.doc.mapper.DocMapper;
import ru.x5.migration.reader.XmlFileReader;

import java.util.List;
import java.util.stream.Stream;

@Service
public class DocService {
    private final XmlFileReader reader;
    private final DocHeaderDao docHeaderDao;
    private final DocDetailDao docDetailDao;
    private final DocMapper mapper;

    public DocService(@Qualifier("docXmlReader") XmlFileReader reader, DocHeaderDao docHeaderDao, DocDetailDao docDetailDao, DocMapper mapper) {
        this.reader = reader;
        this.docHeaderDao = docHeaderDao;
        this.docDetailDao = docDetailDao;
        this.mapper = mapper;
    }

    public void parseXml(String fileName) {
        ParseContext parseContext = reader.read(fileName);
        var result = parseContext.getResult();
        var mtDocConc = (Mt_doc_conc) result.getParsedTags().pollFirst();
        String idSap = "1234";
        var details = mtDocConc.doc.stream()
                .map(doc -> Tuple.of(parseDocDetails(doc.details.detail, doc.header, idSap), parseDocHeader(doc, idSap)))
                .toList();
        List<DocDetail> docDetails = details.stream().flatMap(Tuple2::_1).toList();
        List<DocHeader> docHeaders = details.stream().map(Tuple2::_2).toList();
        docHeaderDao.batchUpdate(docHeaders, docHeaders.size());
        docDetailDao.batchUpdate(docDetails, docDetails.size());
    }

    private Stream<DocDetail> parseDocDetails(List<Details.Detail> details, Header header, String idSap) {
        return details.stream()
                .map(detail -> mapper.toDocDetail(detail, header, idSap));
    }

    private DocHeader parseDocHeader(Doc doc, String idSap) {
        return mapper.toDocHeader(doc.header, idSap);
    }
}
