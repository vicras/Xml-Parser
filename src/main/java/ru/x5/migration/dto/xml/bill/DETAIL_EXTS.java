package ru.x5.migration.dto.xml.bill;

import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.List;

public class DETAIL_EXTS implements XmlFileObject {
    public List<DETAIL_EXT> detail_ext;

    public static class DETAIL_EXT implements XmlFileObject {
        public String ID_DETAIL; // ="4";
        public String EXT_NAME; // ="ID_PROMO_0001";
        public String EXT_TYPE; // ="C";
        public String EXT_NUMBER; // ="43.01 ";
        public String EXT_STRING; // ="101706";
    }
}
