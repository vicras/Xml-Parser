package ru.x5.migration.dto.xml.evsd;

import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.List;

public class Mt_merc_suppl_upload_nq implements XmlFileObject {
    public String werks;
    public String filename;
    public String destination;
    public List<Body> body;
}
