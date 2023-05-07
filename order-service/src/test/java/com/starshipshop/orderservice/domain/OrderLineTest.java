package com.starshipshop.orderservice.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderLineTest {

    @Test
    void create_withValidParam_givesOrderLine() {
        OrderLine result = OrderLine.create("123", BigDecimal.ONE, 11);

        assertThat(result.getSkuCode()).isEqualTo("123");
        assertThat(result.getPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(result.getQuantity()).isEqualTo(11);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    void create_withBlankSkuCode_throwsError() {
        IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> OrderLine.create(" ", BigDecimal.ONE, 11));

        assertThat(result.getMessage()).isEqualTo("Cannot create OrderLine with a blank skuCode");
    }
}
