package com.example.wppl.dto;

import com.example.wppl.domain.E1WPA01;
import com.example.wppl.domain.EDI_DC40;

import java.util.Stack;


public class ActiveElement {
    public Stack<String> currentElement = new Stack<>();

    public E1WPA01 e1WPA01;
    public E1WPA01.E1WPA02 e1WPA02;
    public E1WPA01.E1WPA03 e1WPA03;
    public EDI_DC40 ediDc40;
}
