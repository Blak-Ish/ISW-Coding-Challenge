package com.isw.bookstore.service;

import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.dto.PaymentRequest;
import org.springframework.data.domain.Pageable;

public interface CheckoutService {

    ApiResponse payForBooks(PaymentRequest paymentRequest);

    ApiResponse completePayment(Long orderIs);

    ApiResponse getPurchaseHistory(int pageNo ,int pageSize);
}
