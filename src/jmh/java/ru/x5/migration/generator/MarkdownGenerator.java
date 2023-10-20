package ru.x5.migration.generator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MarkdownGenerator {
    public static void main(String[] args) throws IOException {
        var fileGenerator = new MarkdownGenerator();
        var content = fileGenerator.generateFileContent(10000);
        fileGenerator.generateFile("markdown_big.xml", content);
    }

    private String generateFileContent(int amountOfRecords) {
        StringBuilder tagBuilder = new StringBuilder();
        for (int i = 0; i < amountOfRecords; i++) {
            var row = rowTemplate();
            tagBuilder.append(row)
                    .append("\n");
        }
        return generateFileContent(tagBuilder.toString());
    }

    private String rowTemplate() {
        return String.format(row_Template);
    }

    private String generateFileContent(String rows) {
        return String.format(fileDataTemplate, rows);
    }

    private void generateFile(String fileName, String content) throws IOException {
        Files.write(Paths.get(fileName), List.of(content), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
    }

    private final static String fileDataTemplate =
            """
                    <ns0:mt_mkd WERKS="1004" CREATEDATE="20220101140522" MSGTYPE="MKD" xmlns:ns0="http://x5.ru/">
                            %s   
                    </ns0:mt_mkd>
                    """;
    private final static String row_Template =
            """
                    <row MARKDOWN_ID="2071055" MARKDOWN_CODE="2936899546559" DT_CREATION="20220101140502" DT_CHANGE="
                            20220101140502" PLU="4042365" PRICE="378.95" QTY="0.104" REGULAR_PRICE="832.0" STATUS="3" UOM="KG" TYPE="MAN"
                            SELLINGPRICE="583.0"/>
                    """;
}
