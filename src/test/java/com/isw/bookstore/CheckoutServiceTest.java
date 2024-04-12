package com.isw.bookstore;


import com.isw.bookstore.dto.*;
import com.isw.bookstore.model.Book;
import com.isw.bookstore.model.Genre;
import com.isw.bookstore.model.Order;
import com.isw.bookstore.model.PaymentStatus;
import com.isw.bookstore.repository.OrderRepository;
import com.isw.bookstore.security.SecurityUtil;
import com.isw.bookstore.security.credentials.Merchant;
import com.isw.bookstore.service.ShoppingCartService;
import com.isw.bookstore.service.impl.CheckoutServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CheckoutServiceTest {

    @Mock
    ShoppingCartService cartService ;
    @Mock
    SecurityUtil securityUtil;
    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    CheckoutServiceImpl checkoutService;


    @Test
    void testCheckoutPayment(){
        HashMap<String , CartItems> item = new HashMap<>();
        item.put("1", CartItems.builder().isbn("1000").name("TestBook").bookId(1L).build());
        CartDto dto = new CartDto();
        dto.setCartItems(item);

        when(cartService.getMerchantsCart()).thenReturn(dto);
        when(cartService.emptyCart()).thenReturn(ApiResponse.builder().respCode("00").build());
        when(orderRepository.save(Mockito.any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });

        when(orderRepository.findById(1L)).thenReturn(Optional.of(Order.builder()
                .id(1L)
                .status(PaymentStatus.PENDING)
                .build()));

        PaymentRequest paymentRequest = PaymentRequest.builder().paymentMethod(PaymentMethod.TRANSFER).build();

        ApiResponse apiResponse = checkoutService.payForBooks(paymentRequest);

        assertTrue(apiResponse.getRespBody().toString().contains("1000000001"));


        ApiResponse completePayment = checkoutService.completePayment(1L);

        assertEquals("00", completePayment.getRespCode());


    }

}
