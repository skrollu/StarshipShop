package com.starshipshop.cartservice.repository;

import com.starshipshop.cartservice.repository.jpa.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}