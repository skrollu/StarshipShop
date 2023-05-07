package com.starshipshop.orderservice.web.mapper;

import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.domain.OrderLine;
import com.starshipshop.orderservice.web.response.OrderLineOutputDto;
import com.starshipshop.orderservice.web.response.OrderOutputDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderDtoMapperImplTest {

    @Test
    void mapOrderToOrderDto_withNullOrder_givesNothing() {
        OrderDtoMapper instance = new OrderDtoMapperImpl();

        OrderOutputDto result = instance.mapOrderToOrderOutputDto(null);

        assertThat(result).isNull();
    }

    @Test
    void mapOrderToOrderDto_withValidOrder_givesOrderDto() {
        Order order = Order.builder()
                .id(12L)
                .userId("123")
                .orderDate(LocalDateTime.now())
                .sendingDate(LocalDateTime.now())
                .cancellationDate(LocalDateTime.now())
                .returnDate(LocalDateTime.now())
                .price(BigDecimal.valueOf(123.123))
                .build();

        OrderDtoMapper instance = new OrderDtoMapperImpl();

        OrderOutputDto result = instance.mapOrderToOrderOutputDto(order);

        assertThat(result.getId()).isEqualTo(12L);
        assertThat(result.getOrderDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(result.getSendingDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(result.getCancellationDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(result.getReturnDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(123.123));
        assertThat(result.getOrderLines()).isNull();
    }

    @Test
    void mapOrderToOrderDto_withCompleteOrder_givesCompleteOrderDto() {
        OrderLine line = OrderLine.builder()
                .skuCode("123")
                .price(BigDecimal.valueOf(123))
                .quantity(123)
                .build();
        HashMap map = new HashMap<>();
        map.put("123", line);
        Order order = Order.builder()
                .id(12L)
                .userId("123")
                .orderDate(LocalDateTime.now())
                .sendingDate(LocalDateTime.now())
                .cancellationDate(LocalDateTime.now())
                .returnDate(LocalDateTime.now())
                .price(BigDecimal.valueOf(123.123))
                .orderLines(map)
                .build();

        OrderDtoMapper instance = new OrderDtoMapperImpl();

        OrderOutputDto result = instance.mapOrderToOrderOutputDto(order);

        assertThat(result.getId()).isEqualTo(12L);
        assertThat(result.getOrderDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(result.getSendingDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(result.getCancellationDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(result.getReturnDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(123.123));
        assertThat(result.getOrderLines().size()).isOne();
        assertThat(result.getOrderLines().get("123")).isEqualTo(OrderLineOutputDto.builder()
                .skuCode("123")
                .price(BigDecimal.valueOf(123))
                .quantity(123)
                .build());
    }
}
