package ru.x5.migration.creator.inventory;

import org.springframework.stereotype.Component;
import ru.x5.migration.creator.WithFieldsEntityCreator;
import ru.x5.migration.dto.xml.XmlFileObject;
import ru.x5.migration.dto.xml.inventory.E1WVINH;
import ru.x5.migration.dto.xml.inventory.EDI_DC40;
import ru.x5.migration.dto.xml.inventory.IDOC;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class InventoryCreator extends WithFieldsEntityCreator {

    private static final Map<String, Supplier<XmlFileObject>> NEW_INVENTORY_XML_OBJECT = Map.of(
            "IDOC".toLowerCase(), IDOC::new,
            "EDI_DC40".toLowerCase(), EDI_DC40::new,
            "E1WVINH".toLowerCase(), E1WVINH::new,
            "E1WVINI".toLowerCase(), E1WVINH.E1WVINI::new,
            "E1WXX01".toLowerCase(), E1WVINH.E1WVINI.E1WXX01::new,
            "ZE1WVINH".toLowerCase(), E1WVINH.E1WVINI.ZE1WVINH::new,
            "ZONE".toLowerCase(), E1WVINH.E1WVINI.ZE1WVINH.ZONES.ZONE::new,
            "ZONES".toLowerCase(), E1WVINH.E1WVINI.ZE1WVINH.ZONES::new
    );

    private static final Set<String> FINISHED_TAGS = Stream.of(
                    "E1WVINI", "E1WXX01", "ZE1WVINH", "ZONES", "ZONE", "EDI_DC40", "E1WVINH")
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
