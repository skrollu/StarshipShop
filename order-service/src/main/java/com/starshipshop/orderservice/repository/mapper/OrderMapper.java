package com.starshipshop.orderservice.repository.mapper;

import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.domain.OrderLine;
import com.starshipshop.orderservice.repository.jpa.OrderJpa;
import com.starshipshop.orderservice.repository.jpa.OrderLineJpa;
import org.mapstruct.Mapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Mapper(componentModel = "spring", uses = {})
public interface OrderMapper {

    default Set<OrderLineJpa> mapToSetOfOrderLineJpa(HashMap<String, OrderLine> orderLineHashMap) {
        if (Objects.isNull(orderLineHashMap) || orderLineHashMap.isEmpty()) return null;
        Set<OrderLineJpa> result = new HashSet<>();
        orderLineHashMap.forEach((skuCode, orderLine) -> {
            result.add(mapToOrderLineJpa(orderLine));
        });
        return result;
    }

    default HashMap<String, OrderLine> mapToHashMapOfOrderLine(Set<OrderLineJpa> orderLineJpas) {
        if (Objects.isNull(orderLineJpas) || orderLineJpas.isEmpty()) return null;
        HashMap<String, OrderLine> result = new HashMap<>();
        orderLineJpas.forEach((orderLine) -> {
            result.put(orderLine.getSkuCode(), mapToOrderLine(orderLine));
        });
        return result;
    }

    Order mapToOrder(OrderJpa jpa);

    OrderLine mapToOrderLine(OrderLineJpa jpa);

    OrderJpa mapToOrderJpa(Order order);

    OrderLineJpa mapToOrderLineJpa(OrderLine orderLine);
}
