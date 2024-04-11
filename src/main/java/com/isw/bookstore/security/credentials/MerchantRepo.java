package com.isw.bookstore.security.credentials;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepo extends JpaRepository<Merchant , Long> {

    Merchant findByUsername(String username);
}
