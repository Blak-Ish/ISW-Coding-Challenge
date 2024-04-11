package com.isw.bookstore.controller;

import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.dto.BookDto;
import com.isw.bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartApi {
    private final ShoppingCartService cartService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getMerchantCart() {
        return ResponseEntity.ok(cartService.returnMerchantsCart());
    }

    @PostMapping("/add/{bookId}")
    public ResponseEntity<ApiResponse> addToCart(@PathVariable Long bookId)  {
        return ResponseEntity.ok(cartService.addBookToCart(bookId));
    }

    @PutMapping("/remove/{bookId}")
    public ResponseEntity<ApiResponse> removeFromCart(@PathVariable Long bookId)  {
        return ResponseEntity.ok(cartService.removeBookFromCart(bookId));
    }

    @PutMapping("/clear")
    public ResponseEntity<ApiResponse> clearCart()  {
        return ResponseEntity.ok(cartService.emptyCart());
    }
}
