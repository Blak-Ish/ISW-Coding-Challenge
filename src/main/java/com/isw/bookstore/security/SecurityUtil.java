package com.isw.bookstore.security;

import com.isw.bookstore.security.credentials.Merchant;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityUtil {


    public Merchant getCurrentMerchant(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Merchant user = (Merchant) authentication.getPrincipal();
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException("Merchant not gotten");
        }
        return user;
    }
}
