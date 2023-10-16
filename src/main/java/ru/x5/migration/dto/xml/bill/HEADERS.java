package ru.x5.migration.dto.xml.bill;

import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.List;

public class HEADERS implements XmlFileObject {
    public List<HEADER> header;

    public static class HEADER implements XmlFileObject {
        public HEADER_EXTS header_exts;
        public DETAILS details;
        public PAYMENTS payments;
        public DETAIL_EXTS detail_exts;
        public PAYMENTS_EXTS payments_exts;
    }
}
