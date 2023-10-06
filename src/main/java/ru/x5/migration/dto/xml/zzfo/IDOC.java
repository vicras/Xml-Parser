package ru.x5.migration.dto.xml.zzfo;

import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.List;

public class IDOC implements XmlFileObject {
    public EDI_DC40 edi_dc40;
    public List<ZPROPOSAL_ITEM> zproposal_item;
}
