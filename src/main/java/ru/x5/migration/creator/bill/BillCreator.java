package ru.x5.migration.creator.bill;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.springframework.stereotype.Component;
import ru.x5.migration.creator.WithFieldsEntityCreator;
import ru.x5.migration.dto.xml.XmlFileObject;
import ru.x5.migration.dto.xml.bill.Chk;
import ru.x5.migration.dto.xml.bill.Root;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BillCreator extends WithFieldsEntityCreator {

    private static final Map<String, Supplier<XmlFileObject>> NEW_INVENTORY_XML_OBJECT = Stream.of(
            Tuple.<String, Supplier<XmlFileObject>>of("root".toLowerCase(), Root::new),
            Tuple.<String, Supplier<XmlFileObject>>of("chk".toLowerCase(), Chk::new)
    ).collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));

    private static final Set<String> FINISHED_TAGS = Stream.of("chk")
            .map(String::toLowerCase)
            .collect(Collectors.toSet());

    @Override
    protected Map<String, Supplier<XmlFileObject>> newTagInstanceByNameMap() {
        return NEW_INVENTORY_XML_OBJECT;
    }

    @Override
    protected Set<String> finishedForCompositionTags() {
        return FINISHED_TAGS;
    }
}