package ru.x5.migration.parallel.collector;

import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.context.ParseResult;
import ru.x5.migration.dto.xml.markdown.Mt_mkd;

import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;

public class MarkdownCollector implements Collector<ParseContext, ParseResult, ParseContext> {
    private static final Set<Characteristics> CHARACTERISTICS = Stream.of(CONCURRENT, UNORDERED)
            .collect(Collectors.toSet());

    @Override
    public Supplier<ParseResult> supplier() {
        return ParseResult::new;
    }

    @Override
    public BiConsumer<ParseResult, ParseContext> accumulator() {
        return ((parseResult, context) -> parseResult.getParsedTags().addAll(context.getResult().getParsedTags()));
    }

    @Override
    public BinaryOperator<ParseResult> combiner() {
        return (parseResulA, parseResultB) -> {
            parseResulA.getParsedTags().addAll(parseResultB.getParsedTags());
            return parseResulA;
        };
    }

    @Override
    public Function<ParseResult, ParseContext> finisher() {
        return parseResult -> {
            Mt_mkd mtMkd = new Mt_mkd();
            for (var tag : parseResult.getParsedTags()) {
                if (tag instanceof Mt_mkd mtTag) {
                    mergeMtmkd(mtMkd, mtTag);
                }
            }
            ParseContext context = new ParseContext();
            context.addObjectToResult(mtMkd);
            return context;
        };
    }

    private void mergeMtmkd(Mt_mkd resultMtMkd, Mt_mkd tag) {
        if (Objects.nonNull(tag.MSGTYPE)) {
            resultMtMkd.MSGTYPE = tag.MSGTYPE;
        }
        if (Objects.nonNull(tag.WERKS)) {
            resultMtMkd.WERKS = tag.WERKS;
        }
        if (Objects.nonNull(tag.CREATEDATE)) {
            resultMtMkd.CREATEDATE = tag.CREATEDATE;
        }
        if (Objects.nonNull(tag.row)) {
            resultMtMkd.row.addAll(tag.row);
        }
    }


    @Override
    public Set<Characteristics> characteristics() {
        return CHARACTERISTICS;
    }
}
