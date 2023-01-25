package com.starshipshop.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starshipshop.orderservice.repository.jpa.order.OrderLine;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

}
