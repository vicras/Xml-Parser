package ru.x5.migration.creator.evsd;

import org.springframework.stereotype.Component;
import ru.x5.migration.creator.WithFieldsEntityCreator;
import ru.x5.migration.dto.xml.XmlFileObject;
import ru.x5.migration.dto.xml.evsd.Body;
import ru.x5.migration.dto.xml.evsd.Mt_merc_suppl_upload_nq;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class EvsdCreator extends WithFieldsEntityCreator {

    private static final Map<String, Supplier<XmlFileObject>> NEW_INVENTORY_XML_OBJECT = Map.of(
            "mt_merc_suppl_upload_nq".toLowerCase(), Mt_merc_suppl_upload_nq::new,
            "body".toLowerCase(), Body::new,
            "eVetDoc".toLowerCase(), Body.EVetDoc::new
    );

    private static final Set<String> FINISHED_TAGS = Stream.of("body", "eVetDoc")
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
