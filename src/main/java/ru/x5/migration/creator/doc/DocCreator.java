package ru.x5.migration.creator.doc;

import org.springframework.stereotype.Component;
import ru.x5.migration.creator.WithFieldsEntityCreator;
import ru.x5.migration.dto.xml.XmlFileObject;
import ru.x5.migration.dto.xml.doc.*;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DocCreator extends WithFieldsEntityCreator {

    private static final Map<String, Supplier<XmlFileObject>> NEW_INVENTORY_XML_OBJECT = Map.of(
            "mt_doc_conc".toLowerCase(), Mt_doc_conc::new,
            "DOC".toLowerCase(), Doc::new,
            "PUBLIC".toLowerCase(), Public::new,
            "HEADER".toLowerCase(), Header::new,
            "DETAILS".toLowerCase(), Details::new,
            "DETAIL".toLowerCase(), Details.Detail::new
    );

    private static final Set<String> FINISHED_TAGS = Stream.of("DOC",
                    "HEADER",
                    "PUBLIC",
                    "DETAILS",
                    "DETAIL")
            .map(String::toLowerCase)
            .collect(Collectors.toSet());


    @Override
    protected Map<String, Supplier<XmlFileObject>> newTagInstanceByNameMap() {
        return NEW_INVENTORY_XML_OBJECT;
    }

    @Override
    protected Set<String> finishedForCompositionTags() {
        return FINISHED_TAGS;
    }
}
