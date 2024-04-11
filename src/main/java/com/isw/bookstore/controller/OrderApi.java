package com.isw.bookstore.controller;


import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.dto.PaymentRequest;
import com.isw.bookstore.model.Genre;
import com.isw.bookstore.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderApi {

    private final CheckoutService checkoutService;

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse> checkout(@Validated @RequestBody PaymentRequest paymentRequest)  {
        return ResponseEntity.ok(checkoutService.payForBooks(paymentRequest));
    }

    @PutMapping("/completePayment/{orderId}")
    public ResponseEntity<ApiResponse> completePayment(@PathVariable Long orderId)  {
        return ResponseEntity.ok(checkoutService.completePayment(orderId));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse> getOrderHistory(@RequestParam (defaultValue = "0") int pageNo,
                                                     @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(checkoutService.getPurchaseHistory(pageNo, pageSize));
    }
}
