package com.example.wppl.dto;

import com.example.wppl.domain.E1WPA01;
import com.example.wppl.domain.EDI_DC40;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class ParseResult {
    public ActiveElement activeElements = new ActiveElement();

    public List<EDI_DC40> ediDc40s = new ArrayList<>();
    public List<E1WPA01> e1WPA01s = new ArrayList<>();

    public String peekLastElement() {
        return activeElements.elementOrder.peek();
    }

    public String popLastElement() {
        return activeElements.elementOrder.pop();
    }

    public String getPenultimateElement() {
        String lastElement = activeElements.elementOrder.pop();
        String penultimateElement = activeElements.elementOrder.peek();
        activeElements.elementOrder.push(lastElement);
        return penultimateElement;
    }

    boolean isExistLastElement() {
        return activeElements.elementOrder.isEmpty();
    }

    public ParseResult() {
    }

    public ParseResult(List<EDI_DC40> ediDc40s, List<E1WPA01> e1WPA01s) {
        this.ediDc40s = ediDc40s;
        this.e1WPA01s = e1WPA01s;
    }
}
