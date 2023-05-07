package com.starshipshop.orderservice.service;

import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.domain.OrderLine;

import java.util.HashMap;

public interface OrderService {

    /**
     *
     * @param userId
     * @param orderNumber
     * @return the retrieved Order
     */
    Order getOrder(String userId, Long orderNumber);

    /**
     *
     * @param userId
     * @param orderLines
     * @return Order newly created
     */
    Order createOrder(String userId, HashMap<String, OrderLine> orderLines);
}
