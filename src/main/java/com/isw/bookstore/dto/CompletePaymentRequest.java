package com.isw.bookstore.dto;


import lombok.Data;

@Data
public class CompletePaymentRequest {
    private Long orderId;
    private String otp;
}
