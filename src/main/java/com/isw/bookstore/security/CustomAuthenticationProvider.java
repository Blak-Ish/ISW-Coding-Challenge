package com.isw.bookstore.security;

import com.isw.bookstore.security.credentials.Merchant;
import com.isw.bookstore.security.credentials.MerchantRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationManager {

    private final MerchantRepo merchantRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        // Perform your custom authentication logic here
        // Retrieve user details from userDetailsService and validate the credentials
        // You can throw AuthenticationException if authentication fails
        // Example: retrieving user details by username from UserDetailsService
        Merchant merchant = merchantRepo.findByUsername(username);
        if (merchant == null) {
            throw new UsernameNotFoundException("Invalid credentials");
        }
        // Example: validating credentials
        if (!passwordEncoder.matches(password ,merchant.getPassword())) {
            throw new AuthenticationException("Invalid credentials") {};
        }
        // Create a fully authenticated Authentication object
        Authentication authenticated = new UsernamePasswordAuthenticationToken(merchant, password,   new ArrayList<>());
        return authenticated;
    }

}