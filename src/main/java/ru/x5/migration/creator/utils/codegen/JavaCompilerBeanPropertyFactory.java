package ru.x5.migration.creator.utils.codegen;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.reflections.Reflections;
import ru.x5.migration.creator.utils.codegen.contract.BeanPropertyPerformer;
import ru.x5.migration.creator.utils.codegen.models.GeneratedClassAttributes;
import ru.x5.migration.creator.utils.codegen.models.PropertyPerformerProvider;
import ru.x5.migration.dto.xml.XmlFileObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JavaCompilerBeanPropertyFactory {

    private static final String CLASS_SEARCH_PATH = "ru.x5.migration.dto.xml";
    private static final Class<XmlFileObject> INTERFACE_FOR_IMPLEMENTATION = XmlFileObject.class;

    public static PropertyPerformerProvider bootstrapAllXmlElementProperties() {
        Reflections reflections = new Reflections(CLASS_SEARCH_PATH);
        Map<Class<? extends XmlFileObject>, Map<String, BeanPropertyPerformer>> performerMap =
                reflections.getSubTypesOf(INTERFACE_FOR_IMPLEMENTATION)
                        .stream()
                        .map(clazz -> Tuple.of(clazz, generateForClass(clazz)))
                        .collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));
        return new PropertyPerformerProvider(performerMap);
    }

    public static Map<String, BeanPropertyPerformer> generateForClass(Class<?> beanClass) {
        ClassLoader classLoader = JavaCompilerBeanPropertyFactory.class.getClassLoader();
        StringGeneratedJavaCompilerFacade compilerFacade = new StringGeneratedJavaCompilerFacade(classLoader);

        return Stream.of(beanClass.getFields())
                .map(field -> generateForField(beanClass, field, compilerFacade))
                .collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));
    }

    private static Tuple2<String, BeanPropertyPerformer> generateForField(Class<?> beanClass,
                                                                          Field field,
                                                                          StringGeneratedJavaCompilerFacade compilerFacade) {
        var classAttributes = GeneratedClassAttributes.of(beanClass, field.getName());
        Class<? extends BeanPropertyPerformer> compiledClass = compilerFacade.compile(
                classAttributes.fullClassName(),
                classAttributes.getGeneratedClassSource(field),
                BeanPropertyPerformer.class
        );
        try {
            BeanPropertyPerformer beanPropertyReader = getBeanPropertyPerformerByClass(compiledClass);
            return Tuple.of(field.getName().toLowerCase(), beanPropertyReader);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new IllegalStateException("The generated class (" + classAttributes + ") failed to instantiate.", e);
        }
    }

    private static BeanPropertyPerformer getBeanPropertyPerformerByClass(Class<? extends BeanPropertyPerformer> compiledClass)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? extends BeanPropertyPerformer> constructor = compiledClass.getDeclaredConstructor();
        return constructor.newInstance();
    }
}
