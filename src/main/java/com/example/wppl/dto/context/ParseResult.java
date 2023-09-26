package com.example.wppl.dto.context;

import com.example.wppl.domain.FileObject;
import lombok.Getter;
import lombok.ToString;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Getter
@ToString
public class ParseResult {
    private Deque<FileObject> parsedTags = new LinkedList<>();

    public Optional<FileObject> peekLastTag() {
        return ofNullable(parsedTags.peekFirst());
    }

    public Optional<FileObject> popLastResultTag() {
        return ofNullable(parsedTags.pollFirst());
    }

    public void addTag(FileObject tag) {
         parsedTags.offerFirst(tag);
    }
}
