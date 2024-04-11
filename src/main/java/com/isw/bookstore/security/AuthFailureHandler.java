package com.isw.bookstore.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.isw.bookstore.dto.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final Gson gson;

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {



            log.info("invalid creds");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.addHeader("Content-Type", "application/json");
            response.getWriter().write(gson.toJson(new ApiResponse(exception.getMessage(), "96", HttpStatus.UNAUTHORIZED))
            );


    }


}
