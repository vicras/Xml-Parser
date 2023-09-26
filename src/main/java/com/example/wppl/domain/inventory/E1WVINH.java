package com.example.wppl.domain.inventory;

import com.example.wppl.domain.FileObject;

import java.util.List;

public class E1WVINH implements FileObject {
    public String GJAHR;
    public String XBLNI;
    public String DNAME;
    public String DDATE;
    public String DTIME;
    public String FILIALE;
    public String VPDAT;
    public String KEORD;
    public String ORDNG;
    public List<E1WVINI> E1WVINI;

    public static class E1WVINI implements FileObject {
        public String ZEILI;
        public String QUALARTNR;
        public String ARTNR;
        public String ERFMG;
        public String ERFME;
        public List<E1WXX01> E1WXX01;
        public List<ZE1WVINH> ZE1WVINH;

        public static class E1WXX01 implements FileObject {
            public String FLDGRP;
            public String FLDNAME;
            public String FLDVAL;
        }

        public static class ZE1WVINH implements FileObject {
            public String ZDACT;
            public String ZDATE;
            public String ZTIME;
            public String ZMEMO;
        }
    }
}
