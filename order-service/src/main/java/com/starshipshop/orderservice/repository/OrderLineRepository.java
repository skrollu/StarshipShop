package com.starshipshop.orderservice.repository;

import com.starshipshop.orderservice.repository.jpa.OrderLineJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLineJpa, Long> {

}
