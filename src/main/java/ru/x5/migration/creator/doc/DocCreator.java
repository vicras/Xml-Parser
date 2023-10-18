package ru.x5.migration.creator.doc;

import org.springframework.stereotype.Component;
import ru.x5.migration.creator.WithFieldsEntityCreator;
import ru.x5.migration.dto.xml.XmlFileObject;
import ru.x5.migration.dto.xml.doc.Details;
import ru.x5.migration.dto.xml.doc.Doc;
import ru.x5.migration.dto.xml.doc.Header;
import ru.x5.migration.dto.xml.doc.Mt_doc_conc;
import ru.x5.migration.dto.xml.evsd.Body;
import ru.x5.migration.dto.xml.evsd.Mt_merc_suppl_upload_nq;

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
            "HEADER".toLowerCase(), Header::new,
            "DETAILS".toLowerCase(), Details::new,
            "DETAIL".toLowerCase(), Details.Detail::new
    );

    private static final Set<String> FINISHED_TAGS = Stream.of("DOC",
                    "HEADER",
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
