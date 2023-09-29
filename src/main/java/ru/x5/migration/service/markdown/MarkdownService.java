package ru.x5.migration.service.markdown;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.x5.migration.dao.markdown.MarkdownDao;
import ru.x5.migration.domain.markdown.Markdown;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.markdown.Mt_mkd;
import ru.x5.migration.dto.xml.markdown.mapper.MarkdownMapper;
import ru.x5.migration.reader.XmlFileReader;

import java.util.List;

@Service
public class MarkdownService {

    private final XmlFileReader reader;
    private final MarkdownMapper mapper;
    private final MarkdownDao dao;

    public MarkdownService(@Qualifier("markdownXmlReader") XmlFileReader fullFileReader,
                           MarkdownMapper mapper,
                           MarkdownDao dao) {
        this.reader = fullFileReader;
        this.mapper = mapper;
        this.dao = dao;
    }

    public List<Markdown> parseXml(String fileName) {
        ParseContext parseContext = reader.read(fileName);
        var result = parseContext.getResult();
        var mtMkd = (Mt_mkd) result.getParsedTags().pollFirst();
        var markdowns = mtMkd.row.stream()
                .map(row -> mapper.toMarkdown(mtMkd, row))
                .toList();
        return dao.batchUpdate(markdowns, markdowns.size());
    }
}
