package ru.x5.migration.service.evsd;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.x5.migration.dao.evsd.EvsdDao;
import ru.x5.migration.domain.evsd.EvsdBuffer;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.evsd.Mt_merc_suppl_upload_nq;
import ru.x5.migration.dto.xml.evsd.mapper.EvsdMapper;
import ru.x5.migration.reader.XmlFileReader;

import java.util.List;

@Service
public class EvsdService {
    private final XmlFileReader reader;
    private final EvsdDao evsdDao;
    private final EvsdMapper mapper;

    public EvsdService(@Qualifier("evsdXmlReader") XmlFileReader reader, EvsdDao evsdDao, EvsdMapper mapper) {
        this.reader = reader;
        this.evsdDao = evsdDao;
        this.mapper = mapper;
    }

    public List<EvsdBuffer> parseXml(String fileName) {
        ParseContext parseContext = reader.read(fileName);
        var result = parseContext.getResult();
        var uploadNq = (Mt_merc_suppl_upload_nq) result.getParsedTags().pollFirst();
        var buffers = uploadNq.body.stream()
                .map(mapper::toEvsdBuffer)
                .toList();
        return evsdDao.batchUpdate(buffers, buffers.size());
    }
}
