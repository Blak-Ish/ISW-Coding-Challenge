package com.isw.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.isw.bookstore.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private Long orderId;
    private PaymentStatus status;
    private String bank;
    private String account;
    private String ussd;

}
