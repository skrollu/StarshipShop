package com.starshipshop.orderservice.web.mapper;

import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.domain.OrderLine;
import com.starshipshop.orderservice.repository.jpa.OrderLineJpa;
import com.starshipshop.orderservice.web.request.OrderLineInputDto;
import com.starshipshop.orderservice.web.response.OrderOutputDto;
import org.mapstruct.Mapper;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

    OrderOutputDto mapOrderToOrderOutputDto(Order order);

    default HashMap<String, OrderLine> mapSetOfOrderLinesToHashMap(Set<OrderLineInputDto> inputDtos) {
        if (Objects.isNull(inputDtos) || inputDtos.isEmpty()) return null;
        HashMap<String, OrderLine> result = new HashMap<>();
        inputDtos.forEach((orderLine) -> {
            result.put(orderLine.getSkuCode(), mapToOrderLine(orderLine));
        });
        return result;
    }

    OrderLine mapToOrderLine(OrderLineInputDto inputDto);

}
