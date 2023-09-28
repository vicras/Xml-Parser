package ru.x5.migration.creator;

import ru.x5.migration.creator.utils.CreatorUtils;
import ru.x5.migration.dto.xml.XmlFileObject;
import ru.x5.migration.dto.context.ParseContext;

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
                                CreatorUtils.setTextValue(objToSet, field, value))));
    }

    protected void setObjectValue(ParseContext context) {
        var lastObject = context.popLastObject();
        var parentObject = context.peekLastObject();
        var currentTagName = context.currentTagName();

        parentObject.flatMap(parentObj ->
                lastObject.flatMap(value ->
                        currentTagName.map(field ->
                                CreatorUtils.setObjectValue(parentObj, field, value))));
    }

    protected abstract Optional<XmlFileObject> newTagInstanceByName(String name);

    protected abstract void putTogetherFinishedTag(String name, ParseContext context);
}
