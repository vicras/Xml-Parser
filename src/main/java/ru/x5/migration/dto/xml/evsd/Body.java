package ru.x5.migration.dto.xml.evsd;

import ru.x5.migration.dto.xml.XmlFileObject;

public class Body implements XmlFileObject {

    public EVetDoc eVetDoc;
    public String ProductCode;
    public String ProductItemGTIN;
    public String ProductName;
    public String WayBillNo;
    public String WayBillDate;
    public String PlantCode;


    public static class EVetDoc implements XmlFileObject {
        public String VetDocUUID;
    }
}
