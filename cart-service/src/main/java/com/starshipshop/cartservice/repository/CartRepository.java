package com.starshipshop.cartservice.repository;

import com.starshipshop.cartservice.repository.jpa.CartJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface CartRepository extends JpaRepository<CartJpa, Long> {

    Optional<CartJpa> findByUserId(String userId);
}