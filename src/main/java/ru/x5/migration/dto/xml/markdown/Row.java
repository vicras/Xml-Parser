package ru.x5.migration.dto.xml.markdown;

import ru.x5.migration.dto.xml.XmlFileObject;

public class Row implements XmlFileObject {
    public String MARKDOWN_ID;
    public String MARKDOWN_CODE;
    public String DT_CREATION; //LocalDateTime DT_CREATION; //ldt
    public String DT_CHANGE; //LocalDateTime DT_CHANGE;
    public String PLU;
    public String PRICE;//Double PRICE;
    public String QTY;//Double QTY;
    public String REGULAR_PRICE;//Double REGULAR_PRICE;
    public String STATUS; // Integer STATUS;
    public String UOM;
    public String TYPE;
    public String DT_START;
    public String DT_END;
    public String SELLINGPRICE; //Double SELLINGPRICE;
    public String SAP_ID;
}
