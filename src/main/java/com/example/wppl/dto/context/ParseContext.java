package com.example.wppl.dto.context;

import com.example.wppl.domain.FileObject;
import lombok.Getter;
import lombok.ToString;

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

    public void addNewTagName(String name){
        path.newTag(name);
    }
    public void addNewTagContent(String text){
        path.newContent(text);
    }

    public void addEndTagName(String name) {
        path.endTag(name);
    }

    public Optional<String> currentTagName(){
        return path.currentTagName();
    }

    public Optional<String> currentContentText(){
        return path.currentContentText();
    }

    // region Result operations

    public Optional<FileObject> peekLastObject(){
        return result.peekLastTag();
    }

    public Optional<FileObject> popLastObject(){
        return result.popLastResultTag();
    }

    public void addObjectToResult(FileObject tag){
        result.addTag(tag);
    }

    // endregion
}
