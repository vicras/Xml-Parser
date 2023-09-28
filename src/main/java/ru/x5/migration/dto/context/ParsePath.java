package ru.x5.migration.dto.context;

import ru.x5.migration.dto.context.xml.NamedTag;
import ru.x5.migration.dto.context.xml.TagTextContent;
import ru.x5.migration.dto.context.xml.XmlElement;
import lombok.Getter;
import lombok.ToString;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@Getter
@ToString
public class ParsePath {
    private Deque<XmlElement> tags = new LinkedList<>();

    public Optional<String> currentContentText() {
        var head = tags.peekFirst();
        if (isNull(head) || !(head instanceof TagTextContent)) {
            return empty();
        } else {
            return of(((TagTextContent) head).getText());
        }
    }

    public Optional<String> currentTagName() {
        return tags.stream()
                .filter(NamedTag.class::isInstance)
                .map(tag -> ((NamedTag) tag).getName())
                .findFirst();
    }

    public void newTag(String name) {
        NamedTag namedTag = new NamedTag(name);
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
