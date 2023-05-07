package com.starshipshop.orderservice.adapter;

import com.starshipshop.orderservice.common.exception.ResourceNotFoundException;
import com.starshipshop.orderservice.domain.Order;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

public interface OrderAdapter {

    Order findByUserIdAndOrderNumber(@NotBlank String userId, @NonNull Long orderNumber) throws ResourceNotFoundException;
    Order createOrder(@NonNull Order order);
}