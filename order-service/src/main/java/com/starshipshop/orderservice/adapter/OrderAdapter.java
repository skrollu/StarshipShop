package com.starshipshop.orderservice.adapter;

import com.starshipshop.orderservice.domain.Order;

public interface OrderAdapter {

    Order findByUserIdAndOrderNumber(String userId, String orderNumber);
}