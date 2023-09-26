package com.example.wppl.creator.utils;

import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

public class CreatorUtils {
    public static Object setValue(Object obj, String fieldName, String value) {
        Optional.ofNullable(ReflectionUtils.findField(obj.getClass(), fieldName))
                .ifPresent(field -> {
                            ReflectionUtils.makeAccessible(field);
                            ReflectionUtils.setField(field, obj, value);
                        }
                );
        return obj;
    }

    public static Object setValueToList(Object obj, String fieldName, Object value) {
        Optional.ofNullable(ReflectionUtils.findField(obj.getClass(), fieldName))
                .ifPresent(field -> {
                            ReflectionUtils.makeAccessible(field);
                            var valueList = ((List<Object>) ReflectionUtils.getField(field, obj));
                            valueList = initializeList(valueList, value);
                            ReflectionUtils.setField(field, obj, valueList);
                        }
                );
        return obj;
    }

    private static List<Object> initializeList(List<Object> list, Object value) {
        list = isNull(list) ? new ArrayList<>() : list;
        list.add(value);
        return list;
    }
}
