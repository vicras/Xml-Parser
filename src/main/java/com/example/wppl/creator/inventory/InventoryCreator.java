package com.example.wppl.creator.inventory;

import com.example.wppl.creator.AbstractEntityCreator;
import com.example.wppl.domain.XmlFileObject;
import com.example.wppl.domain.inventory.E1WVINH;
import com.example.wppl.domain.inventory.EDI_DC40;
import com.example.wppl.dto.context.ParseContext;
import org.springframework.stereotype.Component;

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
        }else if ("ZONE".equalsIgnoreCase(name)) {
            return of(new E1WVINH.E1WVINI.ZE1WVINH.ZONES.ZONE());
        }if ("ZONES".equalsIgnoreCase(name)) {
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
        ) {
            setObjectValue(context);
        }
    }
}
