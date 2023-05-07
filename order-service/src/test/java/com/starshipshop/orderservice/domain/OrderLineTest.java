package com.starshipshop.orderservice.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderLineTest {

    @Test
    void constructor_withValidParam_givesOrderLine() {
        OrderLine result = new OrderLine("123", BigDecimal.ONE, 11);

        assertThat(result.getSkuCode()).isEqualTo("123");
        assertThat(result.getPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(result.getQuantity()).isEqualTo(11);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);
    }
}
