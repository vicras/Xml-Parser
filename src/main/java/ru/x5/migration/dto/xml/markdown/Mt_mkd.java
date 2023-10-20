package ru.x5.migration.dto.xml.markdown;

import lombok.ToString;
import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.ArrayList;
import java.util.List;

@ToString
public class Mt_mkd implements XmlFileObject {
    public String WERKS;
    public String CREATEDATE;
    public String MSGTYPE;

    public List<Row> row = new ArrayList<>();
}
