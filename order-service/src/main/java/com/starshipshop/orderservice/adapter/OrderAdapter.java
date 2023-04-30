package com.starshipshop.orderservice.adapter;

import com.starshipshop.orderservice.domain.Order;

public interface OrderAdapter {

    Order findByOrderNumber(String orderNumber);
}