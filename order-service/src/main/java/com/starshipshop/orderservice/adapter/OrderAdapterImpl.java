package com.starshipshop.orderservice.adapter;

import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.repository.OrderRepository;
import com.starshipshop.orderservice.repository.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class OrderAdapterImpl implements OrderAdapter {

    final OrderRepository orderRepository;
    final OrderMapper orderMapper;

    @Override
    public Order findByUserIdAndOrderNumber(String userId, String orderNumber) {
        if (Objects.isNull(userId) || userId.isEmpty()) return null;
        if (Objects.isNull(orderNumber) || orderNumber.isEmpty()) return null;
        return orderRepository.findByUserIdAndOrderNumber(userId, orderNumber)
                .map(orderMapper::mapToOrder)
                .orElse(null);
    }
}
