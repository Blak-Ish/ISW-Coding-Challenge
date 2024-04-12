package com.isw.bookstore;


import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.model.Book;
import com.isw.bookstore.model.Cart;
import com.isw.bookstore.model.Genre;
import com.isw.bookstore.repository.BookRepository;
import com.isw.bookstore.repository.CartRepository;
import com.isw.bookstore.security.SecurityUtil;
import com.isw.bookstore.security.credentials.Merchant;
import com.isw.bookstore.service.impl.ShoppingCartServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ShoppingCartServiceTest {

    @Mock
    SecurityUtil securityUtil;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private ShoppingCartServiceImpl cartService;



    @Test
    void testAddToCart(){
        Merchant merchant = Merchant.builder()
                .id(1L)
                .password("test")
                .username("test")
                .build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(Book.builder()
                .id(1L)
                .title("testbook")
                .yearOfPub("2024")
                .isbn("1000-1000")
                .genre(Genre.MYSTERY)
                .build()));

        when(securityUtil.getCurrentMerchant()).thenReturn(merchant);

        when(cartRepository.save(Mockito.any(Cart.class))).thenAnswer(invocation -> {
            Cart cart = invocation.getArgument(0);
            cart.setId(1L);
            cart.setMerchant(merchant);
            return cart;
        });
        ApiResponse response = cartService.addBookToCart(1L);
        assertEquals("00", response.getRespCode());
    }

    @Test
    void testReturnCart(){
        Merchant merchant = Merchant.builder()
                .id(1L)
                .password("test")
                .username("test")
                .build();
        when(securityUtil.getCurrentMerchant()).thenReturn(merchant);

        when(cartRepository.findByMerchant(Mockito.any(Merchant.class))).thenReturn(Cart.builder()
                        .items("")
                .build());

        when(cartRepository.existsByMerchant(Mockito.any(Merchant.class))).thenReturn(true);

        ApiResponse apiResponse = cartService.returnMerchantsCart();
        assertEquals("00", apiResponse.getRespCode());
    }

    @Test
    void testRemoveFromCart(){
        Merchant merchant = Merchant.builder()
                .id(1L)
                .password("test")
                .username("test")
                .build();
        when(securityUtil.getCurrentMerchant()).thenReturn(merchant);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(Book.builder()
                .id(1L)
                .title("testbook")
                .yearOfPub("2024")
                .isbn("1000-1000")
                .genre(Genre.MYSTERY)
                .build()));

        when(cartRepository.findByMerchant(Mockito.any(Merchant.class))).thenReturn(Cart.builder()
                .items("")
                .build());

        when(cartRepository.existsByMerchant(Mockito.any(Merchant.class))).thenReturn(true);

        ApiResponse apiResponse = cartService.removeBookFromCart(1L);
        assertEquals("00", apiResponse.getRespCode());
    }

    @Test
    void testClearCart(){
        Merchant merchant = Merchant.builder()
                .id(1L)
                .password("test")
                .username("test")
                .build();
        when(securityUtil.getCurrentMerchant()).thenReturn(merchant);

        when(cartRepository.findByMerchant(Mockito.any(Merchant.class))).thenReturn(Cart.builder()
                .items("")
                .build());

        when(cartRepository.existsByMerchant(Mockito.any(Merchant.class))).thenReturn(true);

        ApiResponse apiResponse = cartService.emptyCart();
        assertEquals("00", apiResponse.getRespCode());
    }


//    @BeforeEach
//    void setup(){
//        Merchant merchant = Merchant.builder()
//                .id(1L)
//                .password("test")
//                .username("test")
//                .build();
//        when(bookRepository.findById(1L)).thenReturn(Optional.of(Book.builder()
//                .id(1L)
//                .title("testbook")
//                .yearOfPub("2024")
//                .isbn("1000-1000")
//                .genre(Genre.MYSTERY)
//                .build()));
//
//        when(securityUtil.getCurrentMerchant()).thenReturn(merchant);
//
//        when(cartRepository.save(Mockito.any(Cart.class))).thenAnswer(invocation -> {
//            Cart cart = invocation.getArgument(0);
//            cart.setId(1L);
//            cart.setMerchant(merchant);
//            return cart;
//        });
//    }

}
