package ru.x5.migration.service.billv2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.x5.migration.dao.billv2.BillDao;
import ru.x5.migration.domain.billv2.Bill;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.billv2.Root;
import ru.x5.migration.dto.xml.billv2.mapper.BillV2Mapper;
import ru.x5.migration.reader.XmlFileReader;

import java.util.List;

@Service
public class BillV2Service {
    private final XmlFileReader reader;
    private final BillV2Mapper mapper;
    private final BillDao dao;

    public BillV2Service(@Qualifier("billV2XmlReader") XmlFileReader reader, BillV2Mapper mapper, BillDao dao) {
        this.reader = reader;
        this.mapper = mapper;
        this.dao = dao;
    }

    public List<Bill> parseXml(String fileName) {
        ParseContext parseContext = reader.read(fileName);
        var result = parseContext.getResult();
        var root = (Root) result.getParsedTags().pollFirst();
        var chks = root.chk.stream()
                .map(mapper::toBill)
                .toList();
        return dao.batchUpdate(chks, chks.size());
    }
}
