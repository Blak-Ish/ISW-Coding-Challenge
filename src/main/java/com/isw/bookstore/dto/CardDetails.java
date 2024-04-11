package com.isw.bookstore.dto;


import lombok.Data;

@Data
public class CardDetails {

    private String number;
    private String exYear;
    private String exMonth;
    private String cvv;
    private String pin;
}
