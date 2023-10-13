package ru.x5.migration.creator.utils.codegen.models;

import ru.x5.migration.creator.utils.codegen.contract.BeanPropertyPerformer;
import ru.x5.migration.creator.utils.codegen.contract.EmptyBeanPropertyPerformerImpl;
import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.Map;

public record PropertyPerformerProvider(
        Map<Class<? extends XmlFileObject>, Map<String, BeanPropertyPerformer>> beenPerformers
) {
    private static final EmptyBeanPropertyPerformerImpl DEFAULT_VALUE = EmptyBeanPropertyPerformerImpl.of();

    public BeanPropertyPerformer getPerformer(Class<?> clazz, String fieldName) {
        return beenPerformers.getOrDefault(clazz, Map.of())
                .getOrDefault(fieldName.toLowerCase(), DEFAULT_VALUE);
    }
}
