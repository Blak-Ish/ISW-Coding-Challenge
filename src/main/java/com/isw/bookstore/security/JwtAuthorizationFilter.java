package com.isw.bookstore.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.security.credentials.Merchant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String TOKEN_PREFIX = "Bearer ";
    private final String secret;
    private final Gson gson;


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, String secret, Gson gson) {
        super(authenticationManager);
        this.secret = secret;
        this.gson = gson;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {


        try {
            UsernamePasswordAuthenticationToken auth = getAuthentication(request);
            if (Objects.isNull(auth) && request.getMethod().equals("OPTIONS")){
                chain.doFilter(request,response);
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request,response);
        }catch (Exception ex){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.addHeader("Content-Type", "application/json");
            response.getWriter().write(gson.toJson(new ApiResponse("Unauthorized", "401", HttpStatus.UNAUTHORIZED)));
        }


    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(token) || !token.startsWith(TOKEN_PREFIX)){
            return null;
        }

        String userStr = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token.replace(TOKEN_PREFIX,""))
                .getSubject();

        Merchant user = gson.fromJson(userStr, Merchant.class);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();


        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }
}
