package ru.x5.migration.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.x5.migration.reader.XmlFileReader;

import java.util.concurrent.TimeUnit;

@Fork(2)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.All)
@Warmup(iterations = 3, time = 5)
@Measurement(iterations = 5, time = 5)
public class MarkdownBenchmark {

    private final XmlFileReader reader = Factory.markdownXmlReader();
    private final XmlFileReader parallelReader = Factory.markdownXmlParallelReader();
    private final String filePath = "examples/markdown/markdown_big.xml";

    @Benchmark
    public void reader(Blackhole blackhole) {
        blackhole.consume(reader.read(filePath));
    }

    @Benchmark
    public void parallelReader(Blackhole blackhole) {
        blackhole.consume(parallelReader.read(filePath));
    }
}
