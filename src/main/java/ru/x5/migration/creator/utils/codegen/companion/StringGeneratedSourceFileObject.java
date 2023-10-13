package ru.x5.migration.creator.utils.codegen.companion;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

public final class StringGeneratedSourceFileObject extends SimpleJavaFileObject {

    private final String javaSource;

    public StringGeneratedSourceFileObject(String fullClassName, String javaSource) {
        super(URI.create("string:///" + fullClassName.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.javaSource = javaSource;
    }

    @Override
    public String getCharContent(boolean ignoreEncodingErrors) {
        return javaSource;
    }

}
