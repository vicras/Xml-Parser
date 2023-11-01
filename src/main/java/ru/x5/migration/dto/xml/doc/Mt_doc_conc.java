package ru.x5.migration.dto.xml.doc;

import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.List;

public class Mt_doc_conc implements XmlFileObject {
    public Public pUblic; // can't use "public" as a field, but was need something with equalIgnoreCase with it.
    public List<Doc> doc;
}
