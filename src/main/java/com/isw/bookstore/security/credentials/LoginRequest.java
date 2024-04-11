package com.isw.bookstore.security.credentials;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}
