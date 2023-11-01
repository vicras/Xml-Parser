package ru.x5.migration.dto.xml.doc;

import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.ArrayList;
import java.util.List;

public class Details implements XmlFileObject {
    public List<Detail> detail = new ArrayList<>();

    public static class Detail implements XmlFileObject {
        public String ID_ITEM; //="000000000003983102"
        public String ID_PARENT;
        public String ID_MASTER;
        public String ID_CONSIGN;
        public String ID_TAXSCHEME;
        public String QTY; //="0.092 "
        public String PRICE; //="0.00 "
        public String OVERHEAD;
        public String VAT;
        public String DISCOUNT;
        public String LINENO;
        public String BEWART;
        public String FACT_QTY;
        public String MEINS;
    }
}
