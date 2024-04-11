package com.isw.bookstore.util;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CardUtils {

    public static String MaskCard(String cardNo) {
        String mask = "";
        String pan = "";
        String last4 = "";
        if (StringUtils.isNotBlank(cardNo)) {
            if (cardNo.length() > 6) {
                pan = cardNo.substring(0, 6);
            }

            if (cardNo.length() > 6) {
                last4 = cardNo.substring(cardNo.length() - 4);
            }

            mask = pan + returnMask(cardNo.length() - 10) + last4;
        }

        return mask;
    }

    private static String returnMask(int i) {
        StringBuilder ms = new StringBuilder();

        for(int j = 0; j < i; ++j) {
            ms.append("*");
        }

        return ms.toString();
    }



}
