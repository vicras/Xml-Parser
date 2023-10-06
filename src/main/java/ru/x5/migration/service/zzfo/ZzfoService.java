package ru.x5.migration.service.zzfo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.x5.migration.dao.zzfo.ZzfoDao;
import ru.x5.migration.domain.zzfo.OrderProposal;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.zzfo.IDOC;
import ru.x5.migration.dto.xml.zzfo.mapper.ZzfoMapper;
import ru.x5.migration.reader.XmlFileReader;

import java.util.List;

@Service
public class ZzfoService {

    private final XmlFileReader reader;
    private final ZzfoMapper mapper;
    private final ZzfoDao dao;

    public ZzfoService(@Qualifier("zzfoXmlReader") XmlFileReader reader,
                       ZzfoMapper mapper,
                       ZzfoDao dao) {
        this.reader = reader;
        this.mapper = mapper;
        this.dao = dao;
    }

    public List<OrderProposal> parseXml(String fileName) {
        ParseContext parseContext = reader.read(fileName);
        var result = parseContext.getResult();
        var idoc = (IDOC) result.getParsedTags().pollFirst();
        var orderProposals = idoc.zproposal_item.stream()
                .map(row -> mapper.toOrder(idoc.edi_dc40, row))
                .toList();
        return dao.batchUpdate(orderProposals, orderProposals.size());
    }
}
