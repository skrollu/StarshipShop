package com.starshipshop.orderservice.web.mapper;

import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.web.response.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

    OrderDto mapOrderToOrderDto(Order order);
    Order mapOrderDtoToOrder(OrderDto dto);
}
