package com.example.wppl.service;

import com.example.wppl.dao.E1WPA01_Dao;
import com.example.wppl.dao.EDI_DC40_Dao;
import com.example.wppl.dao.SpecialQuerryDao;
import com.example.wppl.reader.impl.WppParallelReader;
import com.example.wppl.validator.WpplFileValidator;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.List;

@Service
public class WpplService {
    private final WppParallelReader parallelReader;
    private final E1WPA01_Dao e1WPA01Dao;
    private final EDI_DC40_Dao ediDc40Dao;
    private final SpecialQuerryDao specialQuerryDao;

    public WpplService(WppParallelReader parallelReader,
                       E1WPA01_Dao e1WPA01Dao,
                       EDI_DC40_Dao ediDc40Dao,
                       SpecialQuerryDao specialQuerryDao
    ) {
        this.parallelReader = parallelReader;
        this.e1WPA01Dao = e1WPA01Dao;
        this.ediDc40Dao = ediDc40Dao;
        this.specialQuerryDao = specialQuerryDao;
    }

    public List<String> parseFileAndSaveRecords(String pathName) throws Exception {
        var result = parallelReader.read(pathName);
        e1WPA01Dao.saveAll(result.e1WPA01s);
        ediDc40Dao.saveAll(result.ediDc40s);
        return specialQuerryDao.findAllDocnum();
    }

    public boolean isValidXmlFile(String filePathName, String schemaDocPath) throws SAXException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(schemaDocPath));
        var fileValidator = new WpplFileValidator(schema);
        return fileValidator.isValid(filePathName);
    }
}
