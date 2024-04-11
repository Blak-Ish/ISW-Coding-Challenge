package com.isw.bookstore.service;

import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.dto.CartDto;
import com.isw.bookstore.model.Cart;

public interface ShoppingCartService {

    CartDto getMerchantsCart();
    ApiResponse returnMerchantsCart();

    ApiResponse addBookToCart(Long bookId);

    ApiResponse removeBookFromCart(Long bookId);

    ApiResponse emptyCart();

    Cart getCartForMerchant();
}
