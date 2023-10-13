package ru.x5.migration.creator.utils.codegen.contract;

import lombok.NoArgsConstructor;

@NoArgsConstructor(staticName = "of")
public class EmptyBeanPropertyPerformerImpl implements BeanPropertyPerformer {

    @Override
    public Object getValue(Object bean) {
        return null;
    }

    @Override
    public void setValue(Object bean, Object value) {
        // no actions required
    }

    @Override
    public Class<?> fieldType() {
        return Void.class;
    }
}
