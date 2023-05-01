package com.starshipshop.orderservice.adapter;

import com.starshipshop.orderservice.common.exception.ResourceNotFoundException;
import com.starshipshop.orderservice.domain.Order;

import javax.validation.constraints.NotBlank;

public interface OrderAdapter {

    Order findByUserIdAndOrderNumber(@NotBlank String userId, @NotBlank String orderNumber) throws ResourceNotFoundException;
}