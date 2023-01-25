package com.starshipshop.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starshipshop.orderservice.repository.jpa.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderNumber(String orderNumber);
}
