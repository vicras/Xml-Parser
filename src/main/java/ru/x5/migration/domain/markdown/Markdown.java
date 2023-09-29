package ru.x5.migration.domain.markdown;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Markdown {
    private String werks;
    private LocalDateTime createDate;
    private Integer markdownId;
    private String markdownCode;
    private LocalDateTime dtCreation;
    private LocalDateTime dtPrice;
    private LocalDateTime dtChange;
    private String plu;
    private Double price;
    private Double qty;
    private Double regularPrice;
    private String status;
    private String uom;
    private String type;
    private LocalDateTime dtStart;
    private LocalDateTime dtEnd;
    private Double sellingPrice;
    private String sapId;
    private LocalDateTime dateCreated;
}
