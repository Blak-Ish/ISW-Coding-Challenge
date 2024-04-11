package com.isw.bookstore.service.impl;

import com.google.gson.Gson;
import com.isw.bookstore.dto.*;
import com.isw.bookstore.exceptions.*;
import com.isw.bookstore.model.Cart;
import com.isw.bookstore.model.Order;
import com.isw.bookstore.model.PaymentStatus;
import com.isw.bookstore.repository.BookRepository;
import com.isw.bookstore.repository.CartRepository;
import com.isw.bookstore.repository.OrderRepository;
import com.isw.bookstore.security.SecurityUtil;
import com.isw.bookstore.security.credentials.Merchant;
import com.isw.bookstore.service.CheckoutService;
import com.isw.bookstore.service.ShoppingCartService;
import com.isw.bookstore.util.CardUtils;
import com.isw.bookstore.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final SecurityUtil securityUtil;
    private final ValidationUtil validationUtil;
    private final ShoppingCartService cartService;
    private final Gson gson = new Gson();

    @Override
    public ApiResponse payForBooks(PaymentRequest paymentRequest) {
        /**
         * Get logged in merchants cart and confirm if there are items to pay for
         */
        CartDto cart = cartService.getMerchantsCart();

        if(Objects.isNull(cart.getCartItems()) || cart.getCartItems().isEmpty()){
            throw new EmptyCartException("Your cart is empty, you can't make purchases");
        }

        Order order = Order.builder()
                .merchantId(cart.getMerchantId())
                .status(PaymentStatus.PENDING)
                .items(gson.toJson(cart.getCartItems()))
                .build();

        OrderResponse response = OrderResponse.builder()
                .status(order.getStatus())
                .build();

        String customizedMessage;

        switch (paymentRequest.getPaymentMethod()){
            case WEB -> {
                validationUtil.validateRequest(paymentRequest);
                String pan = CardUtils.MaskCard(paymentRequest.getCard().getNumber());
                order.setMaskedPan(pan);
                order.setPaymentMethod(PaymentMethod.WEB);
                order.setCardMonth(paymentRequest.getCard().getExMonth());
                order.setCardYear(paymentRequest.getCard().getExYear());
                /**
                 * Simulate verve card payment
                 * PIN + OTP
                 */
                customizedMessage = "Please enter the OTP sent to +23400000000009";
            }

            case TRANSFER -> {
                order.setPaymentBank("SWITCH DIGITAL BANK");
                order.setPaymentAccount("1000000001");
                response.setBank(order.getPaymentBank());
                response.setAccount(order.getPaymentAccount());
                /**
                 * Simulate payment to an account
                 */
                customizedMessage = "Please complete your purchase by transferring into the account provided";
            }

            case USSD -> {
                order.setUssdCode("*723*0000*12345678#");
                /**
                 * Simulate payment to an account
                 */
                customizedMessage = "Please complete your purchase by dialing the ussd code on your phone";
            }
            default -> throw new ValidationException("Please supply a valid payment method");
        }

        order = orderRepository.save(order);

        response.setOrderId(order.getId());

        return ApiResponse.builder()
                .respCode("00")
                .respDescription(customizedMessage)
                .respBody(response)
                .build();
    }

    @Override
    public ApiResponse completePayment(Long orderId) {

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isEmpty()){
            throw  new NotFoundException("Order ID provided not found");
        }


        Order order = optionalOrder.get();
        if(!order.getStatus().equals(PaymentStatus.PENDING)){
            throw new BookExistsException("Purchase has already been completed");
        }
        order.setStatus(PaymentStatus.COMPLETED);
        orderRepository.save(order);

        cartService.emptyCart();

        return ApiResponse.builder()
                .respCode("00")
                .respDescription("Order completed successfully")
                .build();
    }

    @Override
    public ApiResponse getPurchaseHistory(int pageNo ,int pageSize) {

        Merchant merchant = securityUtil.getCurrentMerchant();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());

        Page<Order> orderPage = orderRepository.findByMerchantId(merchant.getId() , pageable);

        Map<String, Object> pagDetails = new HashMap<>();
        pagDetails.put("size", orderPage.getSize());
        pagDetails.put("numberElements", orderPage.getNumberOfElements());
        pagDetails.put("totalElements", orderPage.getTotalElements());
        pagDetails.put("totalPages", orderPage.getTotalPages());
        pagDetails.put("number", orderPage.getNumber());

        Map<String , Object> data = new HashMap<>();
        data.put("orders", orderPage.getContent());
        data.put("pageDetails", pagDetails);
        return ApiResponse.builder()
                .respCode("00")
                .respBody(data)
                .build();
    }
}
