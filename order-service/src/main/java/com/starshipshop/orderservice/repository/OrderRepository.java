package com.starshipshop.orderservice.repository;

import com.starshipshop.orderservice.repository.jpa.OrderJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderJpa, Long> {

    OrderJpa findByOrderNumber(String orderNumber);
}
