package ru.x5.migration.dto.xml.bill;

import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.List;

public class PAYMENTS_EXTS implements XmlFileObject{
    public List<PAYMENTS_EXT> payments_ext;

    public static class PAYMENTS_EXT implements XmlFileObject {
        public String ID_DEPARTMENT;    //="2176";
        public String ID_PAYMENTS;  //="1";
        public String EXT_NAME; //="PAY_SYSTEM";
        public String EXT_TYPE; //="C";
        public String EXT_STRING;   //="MIR Credit";
    }
}
