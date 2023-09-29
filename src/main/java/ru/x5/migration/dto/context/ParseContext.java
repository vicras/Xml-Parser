package ru.x5.migration.dto.context;

import lombok.Getter;
import lombok.ToString;
import ru.x5.migration.dto.context.xml.NamedTag;
import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.Map;
import java.util.Optional;

@Getter
@ToString
public class ParseContext {
    private final ParsePath path;
    private final ParseResult result;

    public ParseContext() {
        path = new ParsePath();
        result = new ParseResult();
    }

    // region Path operations

    public void addNewTagName(String name, Map<String, String> attributes) {
        path.newTag(name, attributes);
    }

    public void addNewTagContent(String text) {
        path.newContent(text);
    }

    public void addEndTagName(String name) {
        path.endTag(name);
    }

    public Optional<NamedTag> currentTag() {
        return path.currentTag();
    }

    public Optional<String> currentTagName() {
        return path.currentTag()
                .map(NamedTag::getName);
    }

    public Optional<String> currentContentText() {
        return path.currentContentText();
    }

    // region Result operations

    public Optional<XmlFileObject> peekLastObject() {
        return result.peekLastTag();
    }

    public Optional<XmlFileObject> popLastObject() {
        return result.popLastResultTag();
    }

    public void addObjectToResult(XmlFileObject tag) {
        result.addTag(tag);
    }

    // endregion
}
