package ru.x5.migration.dto.xml.inventory;

import lombok.Getter;
import lombok.Setter;
import ru.x5.migration.dto.xml.XmlFileObject;

@Getter
@Setter
public class IDOC implements XmlFileObject {
    public E1WVINH E1WVINH;
    public EDI_DC40 EDI_DC40;
}

