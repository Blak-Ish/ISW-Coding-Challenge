package com.isw.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class CartDto {
    private Map<String ,CartItems> cartItems;
    @JsonIgnore
    private Long merchantId;
}
