package ru.x5.migration.dto.context.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class NamedTag implements XmlElement {
    private String name;
    private Map<String, String> attributes;
}
