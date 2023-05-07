package com.starshipshop.orderservice.adapter;

import com.starshipshop.orderservice.common.exception.ResourceNotFoundException;
import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.repository.OrderRepository;
import com.starshipshop.orderservice.repository.jpa.OrderJpa;
import com.starshipshop.orderservice.repository.mapper.OrderMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Component
public class OrderAdapterImpl implements OrderAdapter {

    final OrderRepository orderRepository;
    final OrderMapper orderMapper;

    @Override
    @Validated
    public Order findByUserIdAndOrderNumber(@NotBlank String userId, @NotNull @NonNull Long orderNumber) throws ResourceNotFoundException {
        if (StringUtils.isBlank(userId))
            throw new IllegalArgumentException("Cannot find order with a blank userId or orderNumber");
        return orderRepository.findByUserIdAndId(userId, orderNumber)
                .map(orderMapper::mapToOrder)
                .orElseThrow(() -> new ResourceNotFoundException("No order found with the given orderNumber %s".formatted(orderNumber)));
    }

    @Override
    public Order createOrder(@NonNull Order order) {
        OrderJpa jpa = orderRepository.save(orderMapper.mapToOrderJpa(order));
        return orderMapper.mapToOrder(jpa);
    }
}
