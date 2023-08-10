package com.example.wppl.dto;

import com.example.wppl.domain.E1WPA01;
import com.example.wppl.domain.EDI_DC40;
import com.example.wppl.domain.mapper.E1WPA01_Filler;
import com.example.wppl.domain.mapper.E1WPA02_Filler;
import com.example.wppl.domain.mapper.E1WPA03_Filler;
import com.example.wppl.domain.mapper.EDI_DC40_Filler;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class ParseResult {
    private static final String e1WPA012 = "E1WPA01";
    private static final String e1WPA022 = "E1WPA02";
    private static final String e1WPA032 = "E1WPA03";
    private static final String ediDc402 = "EDI_DC40";
    private static final List<String> acceptedElement = List.of(e1WPA012, e1WPA022, e1WPA032, ediDc402);

    public ActiveElement activeElement = new ActiveElement();

    public List<EDI_DC40> ediDc40s = new ArrayList<>();
    public List<E1WPA01> e1WPA01s = new ArrayList<>();

    private final EDI_DC40_Filler ediDc40Filler;
    private final E1WPA01_Filler e1WPA01Filler;
    private final E1WPA02_Filler e1WPA02Filler;
    private final E1WPA03_Filler e1WPA03Filler;

    public ParseResult(EDI_DC40_Filler ediDc40Filler, E1WPA01_Filler e1WPA01Filler, E1WPA02_Filler e1WPA02Filler, E1WPA03_Filler e1WPA03Filler) {
        this.ediDc40Filler = ediDc40Filler;
        this.e1WPA01Filler = e1WPA01Filler;
        this.e1WPA02Filler = e1WPA02Filler;
        this.e1WPA03Filler = e1WPA03Filler;
    }

    public void elementFinished(String toDelete) {
        var poppedElement = activeElement.currentElement.pop();
        if (!poppedElement.equals(toDelete))
            throw new RuntimeException("Usage logic failure unbalance of open/close tags");
        if (e1WPA022.equalsIgnoreCase(poppedElement)) {
            activeElement.e1WPA01.e1WPA02 = activeElement.e1WPA02;
            activeElement.e1WPA02 = null;
        }
        if (e1WPA032.equalsIgnoreCase(poppedElement)) {
            activeElement.e1WPA01.e1WPA03 = activeElement.e1WPA03;
            activeElement.e1WPA03 = null;
        }
        if (ediDc402.equalsIgnoreCase(poppedElement)) {
            ediDc40s.add(activeElement.ediDc40);
            activeElement.e1WPA01 = null;
        }
        if (e1WPA012.equalsIgnoreCase(poppedElement)) {
            e1WPA01s.add(activeElement.e1WPA01);
            activeElement.ediDc40 = null;
        }
    }

    public void elementStart(String elementStart) {
        activeElement.currentElement.push(elementStart);
    }

    public void fill(String value) {
        value = value.replaceAll("\\s", "");
        if (activeElement.currentElement.isEmpty() || value.isEmpty()) return;
        String lastElement = activeElement.currentElement.pop();
        String penultimateElement = activeElement.currentElement.peek();
        activeElement.currentElement.push(lastElement);

        if (e1WPA012.equalsIgnoreCase(penultimateElement)) {
            activeElement.e1WPA01 = e1WPA01Filler.fill(activeElement.e1WPA01, lastElement, value);
        }
        if (e1WPA022.equalsIgnoreCase(penultimateElement)) {
            activeElement.e1WPA02 = e1WPA02Filler.fill(activeElement.e1WPA02, lastElement, value);
        }
        if (e1WPA032.equalsIgnoreCase(penultimateElement)) {
            activeElement.e1WPA03 = e1WPA03Filler.fill(activeElement.e1WPA03, lastElement, value);
        }
        if (ediDc402.equalsIgnoreCase(penultimateElement)) {
            activeElement.ediDc40 = ediDc40Filler.fill(activeElement.ediDc40, lastElement, value);
        }
    }

    private void checkMoreThanOneEmpty() {

        if (activeElement.currentElement.size() > 2) {
            throw new RuntimeException("More then two elements are not null, check logic of processing");
        }
    }
}
