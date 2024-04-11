package com.isw.bookstore.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isw.bookstore.security.credentials.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class JsonObjectAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while (Objects.nonNull((line = reader.readLine()))){
                stringBuilder.append(line);
            }
            LoginRequest authRequest = objectMapper.readValue(stringBuilder.toString().replace(" ",""),LoginRequest.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword());
            setDetails(request,token);
//            // Set the authentication token as an attribute in the request
//            request.setAttribute("authenticationToken", token);
            return this.getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
