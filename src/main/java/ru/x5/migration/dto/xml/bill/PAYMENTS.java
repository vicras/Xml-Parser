package ru.x5.migration.dto.xml.bill;

import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.List;

public class PAYMENTS implements XmlFileObject {
    public List<PAYMENT> payment;

    public static class PAYMENT implements XmlFileObject {
        public String ID_PAYMENTS; //="1";
        public String PAY_TYPE; //="2";
        public String TOTAL; //="536.15";
    }
}
