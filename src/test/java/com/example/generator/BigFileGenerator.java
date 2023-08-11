package com.example.generator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;

public class BigFileGenerator {

    public static void main(String[] args) throws IOException {
        var fileGenerator = new BigFileGenerator();
        var content = fileGenerator.generateFileContent(100000);
        fileGenerator.generateFile("WP_PL_100000.xml", content);
    }

    private String generateFileContent(int amountOfRecords){
        var rnd = new Random();
        StringBuilder tagBuilder = new StringBuilder();
        for (int i = 0; i < amountOfRecords; i++) {
            String key = String.valueOf(i);
            var EDI_DC40 = generateEDI_DC40(key, key);
            var E1WPA01 = generateE1WPA01(key, rnd.nextInt(100));
            tagBuilder.append(EDI_DC40)
                    .append("\n")
                    .append(E1WPA01);
        }
        return generateFile(tagBuilder.toString());
    }

    private void generateFile(String fileName, String content) throws IOException {
        Files.write(Paths.get(fileName), List.of(content), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
    }

    private String generateEDI_DC40(String docnum, String RCVPRN){
        return String.format(EDI_DC40_Template, docnum, RCVPRN);
    }
    private String generateE1WPA01(String filiale, int MHDHB){
        return String.format(E1WPA01_Template, filiale, MHDHB);
    }

    private String generateFile(String content){
        return String.format(fileDataTemplate, content);
    }

    private final static String fileDataTemplate =
            """
            <?xml version="1.0" encoding="UTF-8"?>
            <WP_PLU03>
                <IDOC BEGIN="1">
                    %s   
                </IDOC>
            </WP_PLU03>
            """;
    private final static String E1WPA01_Template =
            """
            <E1WPA01 SEGMENT="1">
                        <FILIALE> %s </FILIALE>
                        <AENDKENNZ>MODI</AENDKENNZ>
                        <AKTIVDATUM>20230808</AKTIVDATUM>
                        <AENDDATUM>00000000</AENDDATUM>
                        <HAUPTEAN>4810268030178</HAUPTEAN>
                        <ARTIKELNR>000000000003670593</ARTIKELNR>
                        <POSME>ST</POSME>
                        <E1WPA02 SEGMENT="1">
                            <WARENGR>FR0608002</WARENGR>
                            <VERPGEW>0</VERPGEW>
                            <RABERLAUBT>X</RABERLAUBT>
                            <PRDRUCK>X</PRDRUCK>
                            <ARTIKANZ>X</ARTIKANZ>
                            <MHDHB>%s</MHDHB>
                        </E1WPA02>
                        <E1WPA03 SEGMENT="1">
                            <QUALARTTXT>LTXT</QUALARTTXT>
                            <SPRASCODE>R</SPRASCODE>
                            <TEXT>БЗМЖ САВУШ.Творог ХУТОРОК 300г</TEXT>
                            <LFDNR>0001</LFDNR>
                        </E1WPA03>
            </E1WPA01>
            """;
    private final static String EDI_DC40_Template =
            """
            <EDI_DC40 SEGMENT="1">
                <TABNAM>EDI_DC40</TABNAM>
                <MANDT>500</MANDT>
                <DOCNUM>%s</DOCNUM>
                <DOCREL>701</DOCREL>
                <STATUS>30</STATUS>
                <DIRECT>1</DIRECT>
                <OUTMOD>2</OUTMOD>
                <IDOCTYP>WP_PLU03</IDOCTYP>
                <MESTYP>WP_PLU</MESTYP>
                <SNDPOR>SAPER2</SNDPOR>
                <SNDPRT>KU</SNDPRT>
                <SNDPRN>ER2CLNT500</SNDPRN>
                <RCVPOR>A000000064</RCVPOR>
                <RCVPRT>KU</RCVPRT>
                <RCVPRN>%s</RCVPRN>
                <CREDAT>20230808</CREDAT>
                <CRETIM>130234</CRETIM>
                <SERIAL>20230808130231</SERIAL>
            </EDI_DC40>
            """;
}
