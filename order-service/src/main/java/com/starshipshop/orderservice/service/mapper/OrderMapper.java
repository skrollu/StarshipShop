package com.starshipshop.orderservice.service.mapper;

import org.mapstruct.Mapper;

import com.starshipshop.orderservice.domain.CreateOrderInputDto;
import com.starshipshop.orderservice.domain.OrderDto;
import com.starshipshop.orderservice.domain.OrderLineDto;
import com.starshipshop.orderservice.repository.jpa.order.Order;
import com.starshipshop.orderservice.repository.jpa.order.OrderLine;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    public OrderDto mapOrderToOrderDto(Order order);

    public Order mapCreateOrderInputDtoToOrder(CreateOrderInputDto dto);

    public OrderLine mapOrderLineDtoToOrderLine(OrderLineDto dto);
}
