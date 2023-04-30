package com.starshipshop.orderservice.web.mapper;

import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.domain.OrderLine;
import com.starshipshop.orderservice.web.response.OrderDto;
import com.starshipshop.orderservice.web.response.OrderLineDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderDtoMapperImplTest {

    @Test
    void mapOrderToOrderDto_withNullOrder_givesNothing() {
        OrderDtoMapper instance = new OrderDtoMapperImpl();

        OrderDto result = instance.mapOrderToOrderDto(null);

        assertThat(result).isNull();
    }

    @Test
    void mapOrderToOrderDto_withValidOrder_givesOrderDto() {
        Order order = Order.builder()
                .id(12L)
                .orderNumber("123")
                .userId("123")
                .orderDate(LocalDate.now())
                .sendingDate(LocalDate.now())
                .cancellationDate(LocalDate.now())
                .returnDate(LocalDate.now())
                .price(BigDecimal.valueOf(123.123))
                .build();

        OrderDtoMapper instance = new OrderDtoMapperImpl();

        OrderDto result = instance.mapOrderToOrderDto(order);

        assertThat(result.getId()).isEqualTo(12L);
        assertThat(result.getOrderNumber()).isEqualTo("123");
        assertThat(result.getOrderDate()).isBeforeOrEqualTo(LocalDate.now());
        assertThat(result.getSendingDate()).isBeforeOrEqualTo(LocalDate.now());
        assertThat(result.getCancellationDate()).isBeforeOrEqualTo(LocalDate.now());
        assertThat(result.getReturnDate()).isBeforeOrEqualTo(LocalDate.now());
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
                .orderNumber("123")
                .orderDate(LocalDate.now())
                .sendingDate(LocalDate.now())
                .cancellationDate(LocalDate.now())
                .returnDate(LocalDate.now())
                .price(BigDecimal.valueOf(123.123))
                .orderLines(map)
                .build();

        OrderDtoMapper instance = new OrderDtoMapperImpl();

        OrderDto result = instance.mapOrderToOrderDto(order);

        assertThat(result.getId()).isEqualTo(12L);
        assertThat(result.getOrderNumber()).isEqualTo("123");
        assertThat(result.getOrderDate()).isBeforeOrEqualTo(LocalDate.now());
        assertThat(result.getSendingDate()).isBeforeOrEqualTo(LocalDate.now());
        assertThat(result.getCancellationDate()).isBeforeOrEqualTo(LocalDate.now());
        assertThat(result.getReturnDate()).isBeforeOrEqualTo(LocalDate.now());
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(123.123));
        assertThat(result.getOrderLines().size()).isOne();
        assertThat(result.getOrderLines().get("123")).isEqualTo(OrderLineDto.builder()
                .skuCode("123")
                .price(BigDecimal.valueOf(123))
                .quantity(123)
                .build());
    }
}
