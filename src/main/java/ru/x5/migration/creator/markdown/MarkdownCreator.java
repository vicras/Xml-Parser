package ru.x5.migration.creator.markdown;

import org.springframework.stereotype.Component;
import ru.x5.migration.creator.WithFieldsEntityCreator;
import ru.x5.migration.dto.xml.XmlFileObject;
import ru.x5.migration.dto.xml.markdown.Mt_mkd;
import ru.x5.migration.dto.xml.markdown.Row;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MarkdownCreator extends WithFieldsEntityCreator {

    private final Map<String, Supplier<XmlFileObject>> NEW_MARKDOWN_OBJECT = Map.of(
            "mt_mkd".toLowerCase(), Mt_mkd::new,
            "row".toLowerCase(), Row::new
    );

    private static final Set<String> FINISHED_TAGS = Stream.of("row")
            .map(String::toLowerCase)
            .collect(Collectors.toSet());

    @Override
    protected Map<String, Supplier<XmlFileObject>> newTagInstanceByNameMap() {
        return NEW_MARKDOWN_OBJECT;
    }

    @Override
    protected Set<String> finishedForCompositionTags() {
        return FINISHED_TAGS;
    }
}
