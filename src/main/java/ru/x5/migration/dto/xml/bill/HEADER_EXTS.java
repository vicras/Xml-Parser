package ru.x5.migration.dto.xml.bill;

import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.List;

public class HEADER_EXTS implements XmlFileObject {
    public List<HEADER_EXT> header_ext;

    public static class HEADER_EXT implements XmlFileObject {
        public String ID_DEPARTMENT; // = "2176";
        public String ID_PAYDESK; // = "1";
        public String ID_HEADER; // = "00100000000000004494";
        public String EXT_NAME; // = "DOCNUMBER";
        public String EXT_TYPE; // = "C";
        public String EXT_STRING; // = "28565";
    }
}
