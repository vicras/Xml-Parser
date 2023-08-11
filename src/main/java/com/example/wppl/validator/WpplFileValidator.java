package com.example.wppl.validator;

import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class WpplFileValidator {
    private final Schema schema;

    public WpplFileValidator(Schema schema){
        this.schema = schema;
    }

    public boolean isValid(String xmlPath){
        Validator validator = schema.newValidator();
        try {
            validator.validate(new StreamSource(new File(xmlPath)));
            return true;
        } catch (SAXException | IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}