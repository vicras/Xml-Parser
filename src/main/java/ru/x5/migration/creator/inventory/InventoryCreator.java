package ru.x5.migration.creator.inventory;

import org.springframework.stereotype.Component;
import ru.x5.migration.creator.AbstractEntityCreator;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.XmlFileObject;
import ru.x5.migration.dto.xml.inventory.E1WVINH;
import ru.x5.migration.dto.xml.inventory.EDI_DC40;
import ru.x5.migration.dto.xml.inventory.IDOC;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Component
public class InventoryCreator extends AbstractEntityCreator {

    protected Optional<XmlFileObject> newTagInstanceByName(String name) {
        if ("EDI_DC40".equalsIgnoreCase(name)) {
            return of(new EDI_DC40());
        } else if ("E1WVINH".equalsIgnoreCase(name)) {
            return of(new E1WVINH());
        } else if ("E1WVINI".equalsIgnoreCase(name)) {
            return of(new E1WVINH.E1WVINI());
        } else if ("E1WXX01".equalsIgnoreCase(name)) {
            return of(new E1WVINH.E1WVINI.E1WXX01());
        } else if ("ZE1WVINH".equalsIgnoreCase(name)) {
            return of(new E1WVINH.E1WVINI.ZE1WVINH());
        } else if ("ZONE".equalsIgnoreCase(name)) {
            return of(new E1WVINH.E1WVINI.ZE1WVINH.ZONES.ZONE());
        } else if ("IDOC".equalsIgnoreCase(name)) {
            return of(new IDOC());
        } else if ("ZONES".equalsIgnoreCase(name)) {
            return of(new E1WVINH.E1WVINI.ZE1WVINH.ZONES());
        }
        return empty();
    }

    protected void putTogetherFinishedTag(String name, ParseContext context) {
        if (
                "E1WVINI".equalsIgnoreCase(name)
                        || "E1WXX01".equalsIgnoreCase(name)
                        || "ZE1WVINH".equalsIgnoreCase(name)
                        || "ZONES".equalsIgnoreCase(name)
                        || "ZONE".equalsIgnoreCase(name)
                        || "EDI_DC40".equalsIgnoreCase(name)
                        || "E1WVINH".equalsIgnoreCase(name)
        ) {
            setObjectValue(context);
        }
    }
}
