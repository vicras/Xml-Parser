package ru.x5.migration;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.x5.migration.creator.utils.codegen.JavaCompilerBeanPropertyFactory;
import ru.x5.migration.dto.xml.inventory.E1WVINH;
import ru.x5.migration.dto.xml.markdown.Row;

@SpringBootTest
class RuntimeCodeCompilationTests {

    Row row = new Row();

    {
        {
            row.DT_CHANGE = "codeTest";
        }
    }

    @Test
    void testCompileResult() {
        var reader = JavaCompilerBeanPropertyFactory.generateForClass(row.getClass());
        var readResult = reader.get("DT_CHANGE".toLowerCase()).getValue(row);
        Assertions.assertEquals("codeTest", readResult);
    }

    @Test
    void testCompileResultWithComplexDependency() {
        Assertions.assertDoesNotThrow(() -> JavaCompilerBeanPropertyFactory.generateForClass(E1WVINH.E1WVINI.class));
    }

    @Test
    void testBootstrap() {
        var reader = JavaCompilerBeanPropertyFactory.bootstrapAllXmlElementProperties();
        Assertions.assertNotNull(reader);
        Assertions.assertNotNull(reader.getPerformer(E1WVINH.class, "XBLNI"));
    }
}
