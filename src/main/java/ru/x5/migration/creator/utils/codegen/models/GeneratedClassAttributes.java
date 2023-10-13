package ru.x5.migration.creator.utils.codegen.models;

import ru.x5.migration.creator.utils.codegen.JavaCompilerBeanPropertyFactory;
import ru.x5.migration.creator.utils.codegen.contract.BeanPropertyPerformer;

import java.lang.reflect.Field;

public record GeneratedClassAttributes(Class<?> beanClass, String packageName, String simpleClassName,
                                String fullClassName) {

    public static GeneratedClassAttributes of(Class<?> beanClass, String propertyName) {
        String packageName = generatedClassPackageName(beanClass);
        String simpleClassName = generatedClassSimpleName(beanClass, propertyName);
        String fullClassName = generatedClassFullName(packageName, simpleClassName);
        return new GeneratedClassAttributes(beanClass, packageName, simpleClassName, fullClassName);
    }

    private static String generatedClassFullName(String packageName, String simpleClassName) {
        return packageName + "." + simpleClassName;
    }

    private static String generatedClassSimpleName(Class<?> beanClass, String propertyName) {
        return beanClass.getSimpleName() + "$" + propertyName;
    }

    private static String generatedClassPackageName(Class<?> beanClass) {
        return JavaCompilerBeanPropertyFactory.class.getPackage().getName()
                + ".generated." + beanClass.getPackage().getName();
    }

    public String getGeneratedClassSource(Field field) {
        return "package " + packageName + ";\n"
                + "public class " + simpleClassName + " implements " + BeanPropertyPerformer.class.getName() + " {\n"
                + "    public Object getValue(Object bean) {\n"
                + "        return ((" + getCastName(beanClass) + ") bean)." + field.getName() + ";\n"
                + "    }\n"
                + "\n"
                + "    public void setValue(Object bean, Object value) {\n"
                + "        ((" + getCastName(beanClass) + ") bean)." + field.getName() + " = ((" + getCastName(field.getType()) + ") value);\n"
                + "    }\n"
                + "\n"
                + "    public Class<?> fieldType() {\n"
                + "         return " + getCastName(field.getType()) + ".class;\n"
                + "    }\n"
                + "}";
    }

    private String getCastName(Class<?> clazz) {
        return clazz.getName().replaceAll("\\$", ".");
    }
}