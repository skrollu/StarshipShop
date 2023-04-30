package com.starshipshop.orderservice.service;

import com.starshipshop.orderservice.domain.Order;

public interface OrderService {

    Order getOrder(String orderNumber);
}
