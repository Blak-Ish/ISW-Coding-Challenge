package com.isw.bookstore.service.impl;

import com.google.gson.Gson;
import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.dto.CartDto;
import com.isw.bookstore.dto.CartItems;
import com.isw.bookstore.exceptions.NotFoundException;
import com.isw.bookstore.model.Book;
import com.isw.bookstore.model.Cart;
import com.isw.bookstore.repository.BookRepository;
import com.isw.bookstore.repository.CartRepository;
import com.isw.bookstore.security.SecurityUtil;
import com.isw.bookstore.security.credentials.Merchant;
import com.isw.bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final SecurityUtil securityUtil;
    private final Gson gson = new Gson();
    @Override
    public CartDto getMerchantsCart() {

        Cart cart = getCartForMerchant();
        CartDto dto = new CartDto();
        if(StringUtils.isNotEmpty(cart.getItems())){
            HashMap<String ,CartItems> item = gson.fromJson(cart.getItems(), HashMap.class);
            dto.setCartItems(item);
        }

        dto.setMerchantId(cart.getMerchant().getId());

        return dto;
    }

    @Override
    public ApiResponse returnMerchantsCart() {
        Cart cart = getCartForMerchant();
        CartDto dto = new CartDto();
        if(StringUtils.isNotEmpty(cart.getItems())){
            HashMap<String ,CartItems> item = gson.fromJson(cart.getItems(), HashMap.class);
            dto.setCartItems(item);
        }else {
            dto.setCartItems(new HashMap<>());
        }
        return ApiResponse.builder()
                .respCode("00")
                .respDescription("Cart fetched")
                .respBody(dto)
                .build();
    }

    @Override
    public ApiResponse addBookToCart(Long bookId) {
        Cart cart = getCartForMerchant();

        Optional<Book> bookFetch = bookRepository.findById(bookId);
        if(bookFetch.isEmpty()){
            throw new NotFoundException("Book with Id not found");
        }

        CartDto dto = new CartDto();
        if(StringUtils.isNotEmpty(cart.getItems())){
            HashMap<String ,CartItems> item = (HashMap<String ,CartItems>)gson.fromJson(cart.getItems(), HashMap.class);
            dto.setCartItems(item);
        }

        if(Objects.isNull(dto.getCartItems())){
            dto.setCartItems(new HashMap<>());
        }

        if(!dto.getCartItems().containsKey(bookId.toString())){
            Book book = bookFetch.get();
            dto.getCartItems().put(bookId.toString(),CartItems.builder()
                    .bookId(bookId)
                    .name(book.getTitle())
                    .isbn(book.getIsbn())
                    .build());
        }

        String items = gson.toJson(dto.getCartItems());

        cart.setItems(items);
        cartRepository.save(cart);

        return ApiResponse.builder()
                .respCode("00")
                .respDescription("Book added to cart")
                .respBody(dto)
                .build();
    }

    @Override
    public ApiResponse removeBookFromCart(Long bookId) {
        Cart cart = getCartForMerchant();

        Optional<Book> bookFetch = bookRepository.findById(bookId);
        if(bookFetch.isEmpty()){
            throw new NotFoundException("Book with Id not found");
        }

        CartDto dto = new CartDto();
        if(StringUtils.isNotEmpty(cart.getItems())){
            HashMap<String ,CartItems> item = gson.fromJson(cart.getItems(), HashMap.class);
            item.remove(bookId.toString());
            String items = gson.toJson(item);
            cart.setItems(items);
            dto.setCartItems(item);
        }

        cartRepository.save(cart);

        return ApiResponse.builder()
                .respCode("00")
                .respDescription("Book removed from cart")
                .respBody(dto)
                .build();
    }

    @Override
    public ApiResponse emptyCart() {
        Cart cart = getCartForMerchant();
        cart.setItems(null);
        cartRepository.save(cart);

        return ApiResponse.builder()
                .respCode("00")
                .respDescription("Cart emptied successfully")
                .build();
    }


    public Cart getCartForMerchant(){
        Merchant merchant = securityUtil.getCurrentMerchant();
        Cart cart;
        if(!cartRepository.existsByMerchant(merchant)){
            cart = Cart.builder()
                    .merchant(merchant)
                    .build();
            cart = cartRepository.save(cart);
        }else {
            cart = cartRepository.findByMerchant(merchant);
        }
        return cart ;
    }
}
