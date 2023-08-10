package com.example.wppl.dto;

import com.example.wppl.domain.E1WPA01;
import com.example.wppl.domain.EDI_DC40;

import java.util.Deque;
import java.util.LinkedList;


public class ActiveElement {
    public Deque<String> elementOrder = new LinkedList<>();

    public E1WPA01 e1wpa01;
    public E1WPA01.E1WPA02 e1wpa02;
    public E1WPA01.E1WPA03 e1wpa03;
    public EDI_DC40 ediDc40;
}
