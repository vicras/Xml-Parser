package ru.x5.migration.dto.context;

import lombok.Getter;
import lombok.ToString;
import ru.x5.migration.dto.context.xml.NamedTag;
import ru.x5.migration.dto.context.xml.TagTextContent;
import ru.x5.migration.dto.context.xml.XmlElement;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@Getter
@ToString
public class ParsePath {
    private final Deque<XmlElement> tags = new LinkedList<>();

    public Optional<String> currentContentText() {
        var head = tags.peekFirst();
        if (isNull(head) || !(head instanceof TagTextContent)) {
            return empty();
        } else {
            return of(((TagTextContent) head).getText());
        }
    }

    public Optional<NamedTag> currentTag() {
        return tags.stream()
                .filter(NamedTag.class::isInstance)
                .map(NamedTag.class::cast)
                .findFirst();
    }

    public void newTag(String name, Map<String, String> attributes) {
        NamedTag namedTag = new NamedTag(name, attributes);
        tags.offerFirst(namedTag);
    }

    public void newContent(String name) {
        TagTextContent textContent = new TagTextContent(name);
        tags.offerFirst(textContent);
    }

    public void endTag(String name) {
        while (!tags.isEmpty()) {
            var elem = tags.pollFirst();
            if (elem instanceof NamedTag namedTag) {
                if (Objects.equals(namedTag.getName(), name)) {
                    return;
                } else {
                    throw new RuntimeException("Something went wrong, unexpected closing tag with name — " + name);
                }
            }
        }
    }

    public void endTag() {
        if (!tags.isEmpty() && (tags.peekFirst() instanceof NamedTag)) {
            var tagCloseName = (NamedTag) tags.pollFirst();
            endTag(tagCloseName.getName());
        }
    }
}
