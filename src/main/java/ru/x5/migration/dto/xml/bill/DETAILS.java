package ru.x5.migration.dto.xml.bill;

import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.List;

public class DETAILS implements XmlFileObject {
    public List<DETAIL> detail;

    public static class DETAIL implements XmlFileObject {
        public String ID_DETAIL; // = "1";
        public String ID_PLU; // = "3305976";
        public String QTY; // = "1.000 ";
        public String PRICE; // = "4.99 ";
        public String DISCOUNT; // = "0 ";
        public String CORRECTION; // = "0.00 ";
        public String ID_ONQTY; // = "1";
    }
}
