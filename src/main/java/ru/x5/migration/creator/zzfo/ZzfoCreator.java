package ru.x5.migration.creator.zzfo;

import org.springframework.stereotype.Component;
import ru.x5.migration.creator.WithFieldsEntityCreator;
import ru.x5.migration.dto.xml.XmlFileObject;
import ru.x5.migration.dto.xml.zzfo.EDI_DC40;
import ru.x5.migration.dto.xml.zzfo.IDOC;
import ru.x5.migration.dto.xml.zzfo.ZPROPOSAL_ITEM;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ZzfoCreator extends WithFieldsEntityCreator {

    private final Map<String, Supplier<XmlFileObject>> NEW_ZZFO_OBJECT = Map.of(
            "EDI_DC40".toLowerCase(), EDI_DC40::new,
            "ZPROPOSAL_ITEM".toLowerCase(), ZPROPOSAL_ITEM::new,
            "IDOC".toLowerCase(), IDOC::new
    );

    private static final Set<String> FINISHED_TAGS = Stream.of("EDI_DC40", "ZPROPOSAL_ITEM")
            .map(String::toLowerCase)
            .collect(Collectors.toSet());


    @Override
    protected Map<String, Supplier<XmlFileObject>> newTagInstanceByNameMap() {
        return NEW_ZZFO_OBJECT;
    }

    @Override
    protected Set<String> finishedForCompositionTags() {
        return FINISHED_TAGS;
    }
}
