package com.starshipshop.orderservice.repository.mapper;

import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.domain.OrderLine;
import com.starshipshop.orderservice.repository.jpa.OrderJpa;
import com.starshipshop.orderservice.repository.jpa.OrderLineJpa;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface OrderMapper {
    Order mapToOrder(OrderJpa jpa);
    OrderLine mapToOrderLine(OrderLineJpa jpa);
    OrderJpa mapToOrderJpa(Order order);
    OrderLineJpa mapToOrderLineJpa(OrderLine orderLine);
}
