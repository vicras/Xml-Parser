package ru.x5.migration.creator.utils;

import ru.x5.migration.creator.utils.codegen.JavaCompilerBeanPropertyFactory;
import ru.x5.migration.creator.utils.codegen.models.PropertyPerformerProvider;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class CodeGenPropertySetter implements XmlElementPropertySetter {
    private final PropertyPerformerProvider propertySetters = JavaCompilerBeanPropertyFactory.bootstrapAllXmlElementProperties();


    @Override
    public Object setObjectValue(Object obj, String fieldName, Object value) {
        var propertyPerformer = propertySetters.getPerformer(obj.getClass(), fieldName);

        var oldValue = propertyPerformer.getValue(obj);
        var newValue = prepareValue(oldValue, value, propertyPerformer.fieldType());

        propertyPerformer.setValue(obj, newValue);
        return obj;
    }

    private static Object prepareValue(Object oldValue, Object newValue, Class<?> fieldType) {
        if (fieldType == List.class) {
            oldValue = isNull(oldValue) ? new ArrayList<>() : oldValue;
            ((List<Object>) oldValue).add(newValue);
            return oldValue;
        } else {
            return newValue;
        }
    }
}
