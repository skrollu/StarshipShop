package com.starshipshop.orderservice.repository.mapper;

import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.repository.jpa.OrderJpa;
import com.starshipshop.orderservice.repository.jpa.OrderLineJpa;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderMapperImplTest {

    @Test
    void mapToOrder_nullOrderJpa_givesNullOrder() {
        OrderMapper instance = new OrderMapperImpl();

        Order result = instance.mapToOrder(null);

        assertThat(result).isNull();
    }

    @Test
    void mapToOrder_validOrderJpa_givesOrder() {
        OrderMapper instance = new OrderMapperImpl();
        OrderLineJpa orderLineJpa1 = OrderLineJpa.builder()
                .id(1L)
                .skuCode("123")
                .quantity(11)
                .price(new BigDecimal(1234))
                .build();
        OrderLineJpa orderLineJpa2 = OrderLineJpa.builder()
                .id(2L)
                .skuCode("456")
                .quantity(11)
                .price(new BigDecimal(1234))
                .build();
        OrderJpa orderJpa = OrderJpa.builder()
                .id(1L)
                .orderNumber("1234")
                .orderDate(LocalDate.now())
                .sendingDate(LocalDate.now())
                .cancellationDate(null)
                .returnDate(null)
                .price(new BigDecimal(123))
                .orderLines(Set.of(orderLineJpa1, orderLineJpa2))
                .build();

        Order result = instance.mapToOrder(orderJpa);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getOrderDate()).isEqualTo(LocalDate.now());
        assertThat(result.getSendingDate()).isEqualTo(LocalDate.now());
        assertThat(result.getCancellationDate()).isNull();
        assertThat(result.getReturnDate()).isNull();
        assertThat(result.getPrice()).isEqualTo(new BigDecimal(123));
        assertThat(result.getOrderLines()).isNotNull();
        assertThat(result.getOrderLines().isEmpty()).isFalse();
        assertThat(result.getOrderLines().size()).isEqualTo(2);
    }

    @Test
    void mapToOrderLine() {

    }

    @Test
    void mapToOrderJpa() {
    }

    @Test
    void mapToOrderLineJpa() {
    }
}