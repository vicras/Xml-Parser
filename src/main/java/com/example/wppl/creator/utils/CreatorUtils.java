package com.example.wppl.creator.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

public class CreatorUtils {
    public static Object setTextValue(Object obj, String fieldName, String value) {
        Optional.ofNullable(ReflectionUtils.findField(obj.getClass(), fieldName))
                .ifPresent(field -> {
                            ReflectionUtils.makeAccessible(field);
                            ReflectionUtils.setField(field, obj, value);
                        }
                );
        return obj;
    }

    public static Object setObjectValue(Object obj, String fieldName, Object value) {
        Optional.ofNullable(ReflectionUtils.findField(obj.getClass(), fieldName))
                .ifPresent(field -> {
                            ReflectionUtils.makeAccessible(field);
                            var oldValue =  ReflectionUtils.getField(field, obj);
                            oldValue = prepareValue(oldValue, value, field);
                            ReflectionUtils.setField(field, obj, oldValue);
                        }
                );
        return obj;
    }

    private static Object prepareValue(Object oldValue, Object newValue, Field field) {
        if (field.getType() == List.class) {
            oldValue = isNull(oldValue) ? new ArrayList<>() : oldValue;
            ((List<Object>)oldValue).add(newValue);
            return oldValue;
        }else{
            return newValue;
        }
    }
}
