package com.starshipshop.orderservice.adapter;

import com.starshipshop.orderservice.common.exception.ResourceNotFoundException;
import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.repository.OrderRepository;
import com.starshipshop.orderservice.repository.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Component
public class OrderAdapterImpl implements OrderAdapter {

    final OrderRepository orderRepository;
    final OrderMapper orderMapper;

    @Override
    @Validated
    public Order findByUserIdAndOrderNumber(@NotBlank String userId, @NotBlank String orderNumber) throws ResourceNotFoundException {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(orderNumber))
            throw new IllegalArgumentException("Cannot find order a blank userId or orderNumber");
        return orderRepository.findByUserIdAndOrderNumber(userId, orderNumber)
                .map(orderMapper::mapToOrder)
                .orElseThrow(() -> new ResourceNotFoundException("No order found with the given orderNumber %s".formatted(orderNumber)));
    }
}
