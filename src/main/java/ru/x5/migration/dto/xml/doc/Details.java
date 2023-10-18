package ru.x5.migration.dto.xml.doc;

import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.List;

public class Details implements XmlFileObject {
    public List<Detail> detail;

    public static class Detail implements XmlFileObject {
        public String ID_ITEM; //="000000000003983102"
        public String QTY; //="0.092 "
        public String FACT_QTY; //="0.092 "
        public String PRICE; //="0.00 "
        public String LINENO; //="1"
        public String MEINS; //="KG"
        public String BEWART; //="965"
    }
}
