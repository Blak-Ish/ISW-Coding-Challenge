package com.isw.bookstore.util;

import com.isw.bookstore.dto.CardDetails;
import com.isw.bookstore.dto.PaymentRequest;
import com.isw.bookstore.exceptions.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class ValidationUtil {

    public void validateRequest(PaymentRequest transaction) {

        if(Objects.isNull(transaction.getCard())){
            throw new ValidationException("Please supply card details for web transaction");
        }

        if (transaction.getCard().getNumber() == null) {
            throw new ValidationException("Card number must be provided");
        }

        if (transaction.getCard().getCvv() == null) {
            throw new ValidationException("Card cvv must be provided");
        }

        if (transaction.getCard().getExMonth() == null) {
            throw new ValidationException("Card month expiry must be provided");
        }

        if (transaction.getCard().getExYear() == null) {
            throw new ValidationException("Card month expiry must be provided");
        }

        if (transaction.getCard().getPin() == null) {
            throw new ValidationException("Card pin must be provided");
        }
    }

}
