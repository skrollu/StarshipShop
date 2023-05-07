package com.starshipshop.orderservice.service;

import com.starshipshop.orderservice.adapter.OrderAdapter;
import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.domain.OrderLine;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderAdapter orderAdapter;

    @Override
    public Order getOrder(String userId, Long orderNumber) {
        return orderAdapter.findByUserIdAndOrderNumber(userId, orderNumber);
    }

    @Transactional
    @Override
    public Order createOrder(@NonNull String userId, @NonNull HashMap<String, OrderLine> orderLines) {
        Order order = Order.create(userId, orderLines);
        return orderAdapter.createOrder(order);
    }
}
