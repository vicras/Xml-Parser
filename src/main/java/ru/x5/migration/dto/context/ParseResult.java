package ru.x5.migration.dto.context;

import lombok.Getter;
import lombok.ToString;
import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Getter
@ToString
public class ParseResult {
    private final Deque<XmlFileObject> parsedTags = new LinkedList<>();

    public Optional<XmlFileObject> peekLastTag() {
        return ofNullable(parsedTags.peekFirst());
    }

    public Optional<XmlFileObject> popLastResultTag() {
        return ofNullable(parsedTags.pollFirst());
    }

    public void addTag(XmlFileObject tag) {
        parsedTags.offerFirst(tag);
    }
}
