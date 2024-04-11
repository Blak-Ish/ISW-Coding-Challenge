package com.isw.bookstore.repository;

import com.isw.bookstore.model.Order;
import com.isw.bookstore.security.credentials.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order , Long> {

    Page<Order> findByMerchantId(Long merchant , Pageable pageable);
}
