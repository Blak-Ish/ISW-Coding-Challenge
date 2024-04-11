package com.isw.bookstore.repository;

import com.isw.bookstore.model.Cart;
import com.isw.bookstore.security.credentials.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart , Long> {

    Cart findByMerchant(Merchant merchant);

    Boolean existsByMerchant(Merchant merchant);
}
