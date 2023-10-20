package ru.x5.migration.reader.impl;

import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLInputFactory;
import com.fasterxml.aalto.AsyncXMLStreamReader;
import com.fasterxml.aalto.stax.InputFactoryImpl;
import io.vavr.control.Try;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.x5.migration.dto.context.AsyncParseContext;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.parallel.TagsDivideDecider;
import ru.x5.migration.parallel.XmlFileDivider;
import ru.x5.migration.reader.XmlFileReader;
import ru.x5.migration.reader.parser.AaltoAsyncParser;

import javax.xml.stream.XMLStreamException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
public class AaltoParallelFileReader implements XmlFileReader {

    private final int parserThreadNumbers;

    private final AsyncXMLInputFactory asyncXMLInputFactory;
    private final AaltoAsyncParser aaltoAsyncParser;
    private final XmlFileDivider fileDivider;
    private final TagsDivideDecider tagDivideDecider;

    public AaltoParallelFileReader(AaltoAsyncParser aaltoAsyncParser,
                                   XmlFileDivider fileDivider,
                                   int parserThreadNumbers) {
        this.parserThreadNumbers = parserThreadNumbers;
        this.aaltoAsyncParser = aaltoAsyncParser;
        this.asyncXMLInputFactory = new InputFactoryImpl();
        this.fileDivider = fileDivider;
        this.tagDivideDecider = fileDivider.getTagDivideDecider();
    }

    @Override
    public ParseContext read(String filePath) {
        Queue<String> xmlElementQueue = new ConcurrentLinkedQueue<>();
        AtomicBoolean isFileRead = new AtomicBoolean(false);

        CompletableFuture.runAsync(() -> reader(filePath, xmlElementQueue, isFileRead));

        var parserFutures = Stream.generate(() -> getParserFuture(xmlElementQueue, isFileRead))
                .limit(parserThreadNumbers)
                .toList();
        CompletableFuture.allOf(parserFutures.toArray(CompletableFuture[]::new)).join();

        return parserFutures.stream()
                .map(CompletableFuture::join)
                .collect(fileDivider.getFileCollector());
    }

    @SneakyThrows
    public void reader(String filePath,
                       Queue<String> xmlElementQueue,
                       AtomicBoolean isFileRead) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder oneObjectForParse = new StringBuilder();
            for (String line; (line = br.readLine()) != null; ) {
                if (isNeedToDivide(line)) {
                    xmlElementQueue.add(oneObjectForParse.toString());
                    oneObjectForParse.setLength(0); // in most cases it should be more performance efficient than allocate new one
                }
                oneObjectForParse.append(line).append("\n");
            }
            xmlElementQueue.add(oneObjectForParse.toString());
        }
        isFileRead.set(true);
    }

    private boolean isNeedToDivide(String line) {
        return tagDivideDecider
                .cadDivideByThisTag(line);
    }

    private CompletableFuture<ParseContext> getParserFuture(Queue<String> xmlElementQueue, AtomicBoolean isFileRead) {
        AsyncParseContext asyncContext = new AsyncParseContext(xmlElementQueue, isFileRead, fileDivider.getInitialParseResult());
        Supplier<ParseContext> parseSupplier = () -> Try.of(() -> parser(asyncContext))
                .onFailure(ex -> log.warn("Error during parsing xml doc", ex))
                .getOrElseGet(ex -> asyncContext);
        return CompletableFuture.supplyAsync(parseSupplier);
    }

    public ParseContext parser(AsyncParseContext asyncContext) throws XMLStreamException {
        AsyncXMLStreamReader<AsyncByteArrayFeeder> reader = asyncXMLInputFactory.createAsyncForByteArray();
        aaltoAsyncParser.feedFakeRootElement(reader);
        while (asyncContext.hasNextToHandle()) {
            log.debug("\n\n\nSTART\nThread {} has \nContext State Result \n{} \nPath {}", Thread.currentThread().getName(), asyncContext.getResult(), asyncContext.getPath());
            asyncContext.getStringToPerform()
                    .ifPresent(content -> aaltoAsyncParser.handleFileBytes(content.getBytes(), asyncContext, reader));
            log.debug("\n\n\nEND\nThread {} has \nContext State Result \n{} \nPath {}", Thread.currentThread().getName(), asyncContext.getResult(), asyncContext.getPath());
        }
        Try.run(reader::close)
                .onFailure(ex -> log.info("Problem with closing async reader", ex));
        return asyncContext;
    }
}
