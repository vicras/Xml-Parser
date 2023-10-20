package ru.x5.migration.dto.context;

import lombok.ToString;
import ru.x5.migration.dto.context.xml.XmlElement;
import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.Deque;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

@ToString(callSuper = true)
public class AsyncParseContext extends ParseContext {
    private final Queue<String> xmlElementQueue;
    private final AtomicBoolean isFileRead;

    public AsyncParseContext(Queue<String> xmlElementQueue, AtomicBoolean isFileRead, ParseContext initialParseResult) {
        this.xmlElementQueue = xmlElementQueue;
        this.isFileRead = isFileRead;
        Deque<XmlFileObject> initialResult = initialParseResult.getResult().getParsedTags();
        Deque<XmlElement> tags = initialParseResult.getPath().getTags();
        this.result.getParsedTags().addAll(initialResult);
        this.path.getTags().addAll(tags);
    }

    public boolean isFileRead() {
        return isFileRead.get();
    }

    public boolean isElementQueueEmpty() {
        return xmlElementQueue.isEmpty();
    }

    public boolean hasNextToHandle() {
        return !(isFileRead() && isElementQueueEmpty());
    }

    public Optional<String> getStringToPerform() {
        return Optional.ofNullable(xmlElementQueue.poll());
    }
}
