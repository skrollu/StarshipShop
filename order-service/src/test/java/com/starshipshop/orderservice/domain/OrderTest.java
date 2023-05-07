package com.starshipshop.orderservice.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest {

    @Test
    void create_withNullUserId_throwError() {
        NullPointerException result = assertThrows(NullPointerException.class, () -> Order.create(null, null));

        assertThat(result.getMessage()).isEqualTo("userId is marked non-null but is null");
    }

    @Test
    void create_withNullOrderLines_throwError() {
        NullPointerException result = assertThrows(NullPointerException.class, () -> Order.create("123", null));

        assertThat(result.getMessage()).isEqualTo("orderLines is marked non-null but is null");
    }

    @Test
    void create_withEmptyUserId_throwError() {
        IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> Order.create("", new HashMap<>()));

        assertThat(result.getMessage()).isEqualTo("Cannot create order with a blank userId");
    }


    @Test
    void create_withBlankUserId_throwError() {
        IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> Order.create(" ", new HashMap<>()));

        assertThat(result.getMessage()).isEqualTo("Cannot create order with a blank userId");
    }

    @Test
    void create_withoutOrderLines_throwError() {
        IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> Order.create("123", new HashMap<>()));

        assertThat(result.getMessage()).isEqualTo("Cannot create order without orderLines");
    }

    @Test
    void create_aValidOrder_givesOrder() {
        OrderLine ol1 = OrderLine.builder()
                .skuCode("123")
                .price(BigDecimal.valueOf(123))
                .build();
        OrderLine ol2 = OrderLine.builder()
                .skuCode("456")
                .price(BigDecimal.valueOf(456))
                .build();
        HashMap map = new HashMap();
        map.put("123", ol1);
        map.put("456", ol2);
        Order result = Order.create("123", map);

        assertThat(result.getUserId()).isEqualTo("123");
        assertThat(result.getOrderDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(result.getOrderLines().size()).isEqualTo(map.size());
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);
        // TODO assert price
    }
}
