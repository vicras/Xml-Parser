package ru.x5.migration.dto.xml.inventory;

import lombok.Getter;
import ru.x5.migration.dto.xml.XmlFileObject;

import java.util.List;

public class E1WVINH implements XmlFileObject {
    public String GJAHR;
    public String XBLNI;
    public String DNAME;
    public String DDATE;
    public String DTIME;
    public String FILIALE;
    public String VPDAT;
    public String KEORD;
    public String ORDNG;
    public List<E1WVINI> e1WVINI;

    public static class E1WVINI implements XmlFileObject {
        public String ZEILI;
        public String QUALARTNR;
        public String ARTNR;
        public String ERFMG;
        public String ERFME;
        public List<E1WXX01> E1WXX01;
        public ZE1WVINH ze1Wvinh;

        @Getter
        public static class E1WXX01 implements XmlFileObject {
            public String FLDGRP;
            public String FLDNAME;
            public String FLDVAL;
        }

        public static class ZE1WVINH implements XmlFileObject {
            public String ZDACT;
            public String ZDATE;
            public String ZTIME;
            public String ZMEMO;
            public ZONES zones;

            public static class ZONES implements XmlFileObject {
                public ZONE zone;

                public static class ZONE implements XmlFileObject {
                    public String ZONENAME;
                    public String ZONEQTY;
                }
            }

        }

    }
}
