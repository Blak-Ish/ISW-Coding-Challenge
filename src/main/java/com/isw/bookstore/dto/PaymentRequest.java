package com.isw.bookstore.dto;

import lombok.Data;

@Data
public class PaymentRequest {

    private PaymentMethod paymentMethod;

    private CardDetails card;
}
