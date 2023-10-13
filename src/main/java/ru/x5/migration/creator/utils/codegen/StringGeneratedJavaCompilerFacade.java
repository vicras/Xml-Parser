package ru.x5.migration.creator.utils.codegen;

import ru.x5.migration.creator.utils.codegen.companion.StringGeneratedClassLoader;
import ru.x5.migration.creator.utils.codegen.companion.StringGeneratedJavaFileManager;
import ru.x5.migration.creator.utils.codegen.companion.StringGeneratedSourceFileObject;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import java.io.IOException;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class StringGeneratedJavaCompilerFacade {

    private final StringGeneratedClassLoader classLoader;
    private final JavaCompiler compiler;
    private final DiagnosticCollector<JavaFileObject> diagnosticCollector;

    public StringGeneratedJavaCompilerFacade(ClassLoader loader) {
        compiler = getSystemJavaCompiler();
        classLoader = new StringGeneratedClassLoader(loader);
        diagnosticCollector = new DiagnosticCollector<>();
    }

    private JavaCompiler getSystemJavaCompiler() {
        var compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("Cannot find the system Java compiler.\n"
                    + "Maybe you're using the JRE without the JDK: either the classpath lacks a jar (tools.jar)"
                    + " xor the modulepath lacks a module (java.compiler).");
        }
        return compiler;
    }

    public synchronized <T> Class<? extends T> compile(String fullClassName, String javaSource, Class<T> superType) {
        StringGeneratedSourceFileObject javaFileObject = new StringGeneratedSourceFileObject(fullClassName, javaSource);
        JavaFileManager standardFileManager = compiler.getStandardFileManager(diagnosticCollector, null, null);
        compile(fullClassName, javaSource, standardFileManager, javaFileObject);
        Class<T> compiledClass = loadCompiledClass(fullClassName);
        checkForSupperClassImpementation(superType, compiledClass);
        return compiledClass;
    }

    private void compile(String fullClassName, String javaSource, JavaFileManager standardFileManager, StringGeneratedSourceFileObject fileObject) {
        try (StringGeneratedJavaFileManager javaFileManager = new StringGeneratedJavaFileManager(standardFileManager, classLoader)) {
            CompilationTask task = compiler.getTask(
                    null,
                    javaFileManager,
                    diagnosticCollector,
                    null,
                    null,
                    Collections.singletonList(fileObject)
            );
            boolean isSuccess = task.call();
            if (!isSuccess) {
                handleCompilationErrors(fullClassName, javaSource);
            }
        } catch (IOException e) {
            throw new IllegalStateException("The generated class (" + fullClassName + ") failed to compile because the "
                    + JavaFileManager.class.getSimpleName() + " didn't close.", e);
        }
    }

    private void handleCompilationErrors(String fullClassName, String javaSource) {
        final Pattern linePattern = Pattern.compile("\n");
        String compilationMessages = diagnosticCollector.getDiagnostics().stream()
                .map(d -> d.getKind() + ":[" + d.getLineNumber() + "," + d.getColumnNumber() + "] " + d.getMessage(null)
                        + "\n        " + (d.getLineNumber() <= 0 ? "" : linePattern.splitAsStream(javaSource).skip(d.getLineNumber() - 1).findFirst().orElse("")))
                .collect(Collectors.joining("\n"));
        throw new IllegalStateException("The generated class (" + fullClassName + ") failed to compile.\n"
                + compilationMessages);
    }

    private <T> Class<T> loadCompiledClass(String fullClassName) {
        try {
            return (Class<T>) classLoader.loadClass(fullClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("The generated class (" + fullClassName
                    + ") compiled, but failed to load.", e);
        }
    }

    private static <T> void checkForSupperClassImpementation(Class<T> superType, Class<T> compiledClass) {
        if (!superType.isAssignableFrom(compiledClass)) {
            throw new ClassCastException("The generated compiledClass (" + compiledClass
                    + ") cannot be assigned to the superclass/interface (" + superType + ").");
        }
    }
}

