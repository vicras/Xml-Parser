package ru.x5.migration.creator.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

public class ReflectionPropertySetter implements XmlElementPropertySetter {

    @Override
    public Object setObjectValue(Object obj, String fieldName, Object value) {
        getFieldCaseInsensetive(obj, fieldName)
                .ifPresent(field -> {
                            ReflectionUtils.makeAccessible(field);
                            var oldValue = ReflectionUtils.getField(field, obj);
                            oldValue = prepareValue(oldValue, value, field);
                            ReflectionUtils.setField(field, obj, oldValue);
                        }
                );
        return obj;
    }

    private static Optional<Field> getFieldCaseInsensetive(Object obj, String fieldName) {
        Field[] fields = obj.getClass().getDeclaredFields();
        return Arrays.stream(fields).filter(field -> field.getName().equalsIgnoreCase(fieldName))
                .findFirst();
    }

    private static Object prepareValue(Object oldValue, Object newValue, Field field) {
        if (field.getType() == List.class) {
            oldValue = isNull(oldValue) ? new ArrayList<>() : oldValue;
            ((List<Object>) oldValue).add(newValue);
            return oldValue;
        } else {
            return newValue;
        }
    }
}
