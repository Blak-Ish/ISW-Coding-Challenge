package com.isw.bookstore.service;

import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.dto.CartDto;
import com.isw.bookstore.model.Cart;

/**
 * Interface for managing the shopping cart.
 * This interface defines methods for adding books to the cart,
 * removing books from the cart, and retrieving the cart contents.
 * Implementations of this interface should provide functionality
 * to interact with the cart repository and perform cart operations.
 */
public interface ShoppingCartService {

    /**
     * Returns currently loggedin users cart with a CartDto object
     * @return CartDto
     */
    CartDto getMerchantsCart();

    /**
     * Returns currently loggedin users cart
     * @return ApiResponse
     */
    ApiResponse returnMerchantsCart();

    /**
     * Add book to a cart
     * @param bookId
     * @return ApiResponse
     */
    ApiResponse addBookToCart(Long bookId);

    /**
     * Remove a book from a merchants Cart
     * @param bookId
     * @return ApiResponse
     */
    ApiResponse removeBookFromCart(Long bookId);

    /**
     * Clear a merchants cart
     * @return
     */
    ApiResponse emptyCart();


    /**
     * Returns currently loggedin users cart with a Cart Model object
     * @return Cart
     */
    Cart getCartForMerchant();
}
