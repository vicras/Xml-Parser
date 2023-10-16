package ru.x5.migration.creator.bill;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.springframework.stereotype.Component;
import ru.x5.migration.creator.WithFieldsEntityCreator;
import ru.x5.migration.dto.xml.XmlFileObject;
import ru.x5.migration.dto.xml.bill.*;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BillCreator extends WithFieldsEntityCreator {

    private static final Map<String, Supplier<XmlFileObject>> NEW_INVENTORY_XML_OBJECT = Stream.of(
            Tuple.<String, Supplier<XmlFileObject>>of("mt_chk".toLowerCase(), Mt_chk::new),
            Tuple.<String, Supplier<XmlFileObject>>of("HEADERS".toLowerCase(), HEADERS::new),
            Tuple.<String, Supplier<XmlFileObject>>of("HEADER".toLowerCase(), HEADERS.HEADER::new),
            Tuple.<String, Supplier<XmlFileObject>>of("HEADER_EXTS".toLowerCase(), HEADER_EXTS::new),
            Tuple.<String, Supplier<XmlFileObject>>of("HEADER_EXT".toLowerCase(), HEADER_EXTS.HEADER_EXT::new),
            Tuple.<String, Supplier<XmlFileObject>>of("DETAILS".toLowerCase(), DETAILS::new),
            Tuple.<String, Supplier<XmlFileObject>>of("DETAIL".toLowerCase(), DETAILS.DETAIL::new),
            Tuple.<String, Supplier<XmlFileObject>>of("PAYMENTS".toLowerCase(), PAYMENTS::new),
            Tuple.<String, Supplier<XmlFileObject>>of("PAYMENT".toLowerCase(), PAYMENTS.PAYMENT::new),
            Tuple.<String, Supplier<XmlFileObject>>of("DETAIL_EXTS".toLowerCase(), DETAIL_EXTS::new),
            Tuple.<String, Supplier<XmlFileObject>>of("DETAIL_EXT".toLowerCase(), DETAIL_EXTS.DETAIL_EXT::new),
            Tuple.<String, Supplier<XmlFileObject>>of("PAYMENTS_EXTS".toLowerCase(), PAYMENTS_EXTS::new),
            Tuple.<String, Supplier<XmlFileObject>>of("PAYMENTS_EXT".toLowerCase(), PAYMENTS_EXTS.PAYMENTS_EXT::new)
    ).collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));

    private static final Set<String> FINISHED_TAGS = Stream.of("HEADERS", "HEADER",
                    "HEADER_EXTS", "HEADER_EXT", "DETAILS", "DETAIL", "PAYMENTS", "PAYMENT", "DETAIL_EXTS",
                    "DETAIL_EXT", "PAYMENTS_EXTS", "PAYMENTS_EXT"
            )
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