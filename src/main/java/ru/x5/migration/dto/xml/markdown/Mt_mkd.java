package ru.x5.migration.dto.xml.markdown;

import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.List;

public class Mt_mkd implements XmlFileObject {
    public String WERKS;
    public String CREATEDATE;
    public String MSGTYPE;

    public List<Row> row;
}
