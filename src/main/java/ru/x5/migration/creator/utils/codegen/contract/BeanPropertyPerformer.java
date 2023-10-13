package ru.x5.migration.creator.utils.codegen.contract;

public interface BeanPropertyPerformer extends BeanPropertyReader, BeanPropertyWriter {
    Class<?> fieldType();
}
