package com.isw.bookstore.service;

import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.dto.PaymentRequest;
import org.springframework.data.domain.Pageable;

/**
 * Interface to manage checkout and payment functionalities
 * fot the bookstore
 */
public interface CheckoutService {

    /**
     * Start payment after books have been confirmed on the cart ,
     * throws error when no books are found on the merchants cart
     * @param paymentRequest
     * @return
     */
    ApiResponse payForBooks(PaymentRequest paymentRequest);

    /**
     * Complete a pending payment , error is thrown when order is not pending
     * @param orderIs
     * @return
     */
    ApiResponse completePayment(Long orderIs);

    /**
     * Get purchase history for merchant , returns a paginated record of the purchases
     * @param pageNo
     * @param pageSize
     * @return
     */
    ApiResponse getPurchaseHistory(int pageNo ,int pageSize);
}
