package com.example.wppl.creator;

import com.example.wppl.creator.utils.CreatorUtils;
import com.example.wppl.domain.FileObject;
import com.example.wppl.dto.context.ParseContext;

import java.util.Optional;

public abstract class AbstractEntityCreator implements EntityCreator {

    @Override
    public void newElement(ParseContext context) {
        context.currentTagName()
                .flatMap(this::newTagInstanceByName)
                .ifPresent(context::addObjectToResult);
    }

    @Override
    public void endElement(ParseContext context) {
        context.currentTagName()
                .ifPresent(tagName -> putTogetherFinishedTag(tagName, context));
    }

    @Override
    public void setValue(ParseContext context) {
        setPlainFieldValue(context);
    }

    protected void setPlainFieldValue(ParseContext context) {
        var lastObject = context.peekLastObject();
        var currentTagName = context.currentTagName();
        var currentText = context.currentContentText();
        lastObject.flatMap(objToSet ->
                currentText.flatMap(value ->
                        currentTagName.map(field ->
                                CreatorUtils.setValue(objToSet, field, value))));
    }

    protected void setObjectValue(ParseContext context) {
        var lastObject = context.popLastObject();
        var parentObject = context.peekLastObject();
        var currentTagName = context.currentTagName();

        parentObject.flatMap(parentObj ->
                lastObject.flatMap(value ->
                        currentTagName.map(field ->
                                CreatorUtils.setValueToList(parentObj, field, value))));
    }

    protected abstract Optional<FileObject> newTagInstanceByName(String name);

    protected abstract void putTogetherFinishedTag(String name, ParseContext context);
}
