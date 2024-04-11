package com.isw.bookstore.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.isw.bookstore.dto.PaymentMethod;
import com.isw.bookstore.security.credentials.Merchant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "orders")
public class Order {

    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JsonIgnore
    private Long merchantId;

    //Persist items as large object , empty upon purchase payment
    @Lob
    private String items;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String maskedPan;
    private String cardYear;
    private String cardMonth;
    private String paymentAccount;
    private String paymentBank;
    private String ussdCode;

}
