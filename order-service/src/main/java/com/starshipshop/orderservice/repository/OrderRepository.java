package com.starshipshop.orderservice.repository;

import com.starshipshop.orderservice.repository.jpa.OrderJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderJpa, Long> {

    Optional<OrderJpa> findByUserIdAndId(String userId, Long id);
}
