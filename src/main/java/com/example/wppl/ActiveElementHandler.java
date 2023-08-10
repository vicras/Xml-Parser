package com.example.wppl;

import com.example.wppl.domain.mapper.E1WPA01_Filler;
import com.example.wppl.domain.mapper.E1WPA02_Filler;
import com.example.wppl.domain.mapper.E1WPA03_Filler;
import com.example.wppl.domain.mapper.EDI_DC40_Filler;
import com.example.wppl.dto.ParseResult;
import org.springframework.stereotype.Service;

@Service
public class ActiveElementHandler {

    private static final String E_1_WPA_01 = "E1WPA01";
    private static final String E_1_WPA_02 = "E1WPA02";
    private static final String E_1_WPA_03 = "E1WPA03";
    private static final String EDI_DC_40 = "EDI_DC40";

    private final EDI_DC40_Filler ediDc40Filler;
    private final E1WPA01_Filler e1WPA01Filler;
    private final E1WPA02_Filler e1WPA02Filler;
    private final E1WPA03_Filler e1WPA03Filler;

    public ActiveElementHandler(EDI_DC40_Filler ediDc40Filler,
                                E1WPA01_Filler e1WPA01Filler,
                                E1WPA02_Filler e1WPA02Filler,
                                E1WPA03_Filler e1WPA03Filler
    ) {
        this.ediDc40Filler = ediDc40Filler;
        this.e1WPA01Filler = e1WPA01Filler;
        this.e1WPA02Filler = e1WPA02Filler;
        this.e1WPA03Filler = e1WPA03Filler;
    }

    public void elementStart(ParseResult parseResult, String elementStart) {
        var activeElement = parseResult.activeElements;
        activeElement.elementOrder.push(elementStart);
    }

    public void fill(ParseResult parseResult, String value) {
        var activeElement = parseResult.activeElements;

        value = value.replaceAll("\\s", "");
        if (activeElement.elementOrder.isEmpty() || value.isEmpty()) return;
        String lastElement = activeElement.elementOrder.pop();
        String penultimateElement = activeElement.elementOrder.peek();
        activeElement.elementOrder.push(lastElement);

        if (E_1_WPA_01.equalsIgnoreCase(penultimateElement)) {
            activeElement.e1wpa01 = e1WPA01Filler.fill(activeElement.e1wpa01, lastElement, value);
        }
        if (E_1_WPA_02.equalsIgnoreCase(penultimateElement)) {
            activeElement.e1wpa02 = e1WPA02Filler.fill(activeElement.e1wpa02, lastElement, value);
        }
        if (E_1_WPA_03.equalsIgnoreCase(penultimateElement)) {
            activeElement.e1wpa03 = e1WPA03Filler.fill(activeElement.e1wpa03, lastElement, value);
        }
        if (EDI_DC_40.equalsIgnoreCase(penultimateElement)) {
            activeElement.ediDc40 = ediDc40Filler.fill(activeElement.ediDc40, lastElement, value);
        }
    }

    public void elementFinished(ParseResult parseResult, String toDelete) {
        var activeElement = parseResult.activeElements;
        var poppedElement = parseResult.popLastElement();
        if (!poppedElement.equals(toDelete))
            throw new RuntimeException("Usage logic failure unbalance of open/close tags");
        if (E_1_WPA_02.equalsIgnoreCase(poppedElement)) {
            activeElement.e1wpa01.e1WPA02 = activeElement.e1wpa02;
            activeElement.e1wpa02 = null;
        }
        if (E_1_WPA_03.equalsIgnoreCase(poppedElement)) {
            activeElement.e1wpa01.e1WPA03 = activeElement.e1wpa03;
            activeElement.e1wpa03 = null;
        }
        if (EDI_DC_40.equalsIgnoreCase(poppedElement)) {
            parseResult.ediDc40s.add(activeElement.ediDc40);
            activeElement.e1wpa01 = null;
        }
        if (E_1_WPA_01.equalsIgnoreCase(poppedElement)) {
            parseResult.e1WPA01s.add(activeElement.e1wpa01);
            activeElement.ediDc40 = null;
        }
    }

}
