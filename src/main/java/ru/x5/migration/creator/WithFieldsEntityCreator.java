package ru.x5.migration.creator;

import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.Optional.ofNullable;

public abstract class WithFieldsEntityCreator extends AbstractEntityCreator {

    @Override
    protected Optional<XmlFileObject> newTagInstanceByName(String name) {
        return ofNullable(newTagInstanceByNameMap().getOrDefault(name.toLowerCase(), () -> null).get());
    }

    protected abstract Map<String, Supplier<XmlFileObject>> newTagInstanceByNameMap();

    protected void putTogetherFinishedTag(String name, ParseContext context) {
        if (finishedForCompositionTags().contains(name.toLowerCase())) {
            setObjectValue(context);
        }
    }

    protected abstract Set<String> finishedForCompositionTags();
}
