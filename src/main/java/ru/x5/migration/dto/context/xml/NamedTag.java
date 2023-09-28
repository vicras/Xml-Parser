package ru.x5.migration.dto.context.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NamedTag implements XmlElement {
    private String name;
}
