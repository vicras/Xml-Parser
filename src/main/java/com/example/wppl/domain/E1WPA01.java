package com.example.wppl.domain;

import lombok.ToString;

@ToString
public class E1WPA01 {
    public String FILIALE;
    public String AENDKENNZ;
    public String AKTIVDATUM;
    public String AENDDATUM;
    public String HAUPTEAN;
    public String ARTIKELNR;
    public String POSME;

    public E1WPA02 e1WPA02;
    public E1WPA03 e1WPA03;

    @ToString
    public static class E1WPA02 {
        public String WARENGR;
        public String VERPGEW;
        public String RABERLAUBT;
        public String PRDRUCK;
        public String ARTIKANZ;
        public String MHDHB;
    }

    @ToString
    public static class E1WPA03 {
        public String QUALARTTXT;
        public String SPRASCODE;
        public String TEXT;
        public String LFDNR;
    }
}
