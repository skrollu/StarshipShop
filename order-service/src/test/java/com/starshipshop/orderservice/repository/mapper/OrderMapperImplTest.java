package com.starshipshop.orderservice.repository.mapper;

import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.domain.OrderLine;
import com.starshipshop.orderservice.repository.jpa.OrderJpa;
import com.starshipshop.orderservice.repository.jpa.OrderLineJpa;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderMapperImplTest {

    @Test
    void mapToSetOfOrderLineJpa_nullObject_givesNothing() {
        OrderMapper instance = new OrderMapperImpl();

        Set<OrderLineJpa> result = instance.mapToSetOfOrderLineJpa(null);

        assertThat(result).isNull();
    }

    @Test
    void mapToSetOfOrderLineJpa_emptyObject_givesNothing() {
        OrderMapper instance = new OrderMapperImpl();

        Set<OrderLineJpa> result = instance.mapToSetOfOrderLineJpa(new HashMap<>());

        assertThat(result).isNull();
    }

    @Test
    void mapToSetOfOrderLineJpa_validMapOfOrderLine_givesASetOfOrderLineJpa() {
        OrderMapper instance = new OrderMapperImpl();
        HashMap map = new HashMap<>();
        OrderLine orderLine1 = OrderLine.builder()
                .skuCode("123")
                .quantity(11)
                .price(new BigDecimal(1234))
                .build();
        OrderLine orderLine2 = OrderLine.builder()
                .skuCode("456")
                .quantity(11)
                .price(new BigDecimal(1234))
                .build();
        map.put("123", orderLine1);
        map.put("456", orderLine2);

        Set<OrderLineJpa> result = instance.mapToSetOfOrderLineJpa(map);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void mapToHashMapOfOrderLine_nullHashSet_givesNothing() {
        OrderMapper instance = new OrderMapperImpl();

        HashMap<String, OrderLine> result = instance.mapToHashMapOfOrderLine(null);

        assertThat(result).isNull();
    }

    @Test
    void mapToHashMapOfOrderLine_emptyHashSet_aMapOfOrderLines() {
        OrderMapper instance = new OrderMapperImpl();
        HashSet set = new HashSet<OrderLineJpa>();
        OrderLineJpa orderLineJpa1 = OrderLineJpa.builder()
                .skuCode("123")
                .quantity(11)
                .price(new BigDecimal(1234))
                .build();
        OrderLineJpa orderLineJpa2 = OrderLineJpa.builder()
                .skuCode("456")
                .quantity(11)
                .price(new BigDecimal(1234))
                .build();
        set.add(orderLineJpa1);
        set.add(orderLineJpa2);

        HashMap<String, OrderLine> result = instance.mapToHashMapOfOrderLine(set);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get("123")).isNotNull();
        assertThat(result.get("456")).isNotNull();
    }

    @Test
    void mapToHashMapOfOrderLine_validHashSet_givesNothing() {
        OrderMapper instance = new OrderMapperImpl();

        HashMap<String, OrderLine> result = instance.mapToHashMapOfOrderLine(new HashSet<OrderLineJpa>());

        assertThat(result).isNull();
    }

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
    void mapToOrder_validOrderJpaWithoutOrderLines_givesOrder() {
        OrderMapper instance = new OrderMapperImpl();
        OrderJpa orderJpa = OrderJpa.builder()
                .id(1L)
                .orderDate(LocalDate.now())
                .sendingDate(LocalDate.now())
                .cancellationDate(null)
                .returnDate(null)
                .price(new BigDecimal(123))
                .orderLines(new HashSet<>())
                .build();

        Order result = instance.mapToOrder(orderJpa);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getOrderDate()).isEqualTo(LocalDate.now());
        assertThat(result.getSendingDate()).isEqualTo(LocalDate.now());
        assertThat(result.getCancellationDate()).isNull();
        assertThat(result.getReturnDate()).isNull();
        assertThat(result.getPrice()).isEqualTo(new BigDecimal(123));
        assertThat(result.getOrderLines()).isNull();
    }

    @Test
    void mapToOrderLine_withNullOrderLineJpa_givesNullOrderLine() {
        OrderMapper instance = new OrderMapperImpl();

        OrderLine result = instance.mapToOrderLine(null);

        assertThat(result).isNull();
    }

    @Test
    void mapToOrderLine_withOrderLineJpa_givesOrderLine() {
        OrderMapper instance = new OrderMapperImpl();
        OrderLineJpa orderLineJpa = OrderLineJpa.builder()
                .id(1L)
                .skuCode("123")
                .quantity(11)
                .price(BigDecimal.valueOf(11.11))
                .build();

        OrderLine result = instance.mapToOrderLine(orderLineJpa);

        assertThat(result).isNotNull();
        assertThat(result.getSkuCode()).isEqualTo("123");
        assertThat(result.getQuantity()).isEqualTo(11);
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(11.11));
    }

    @Test
    void mapToOrderJpa_withNullOrder_givesNothing() {
        OrderMapper instance = new OrderMapperImpl();

        OrderJpa result = instance.mapToOrderJpa(null);

        assertThat(result).isNull();
    }

    @Test
    void mapToOrderJpa_withValidOrder_giveOrderJpa() {
        OrderMapper instance = new OrderMapperImpl();
        OrderLine orderLine1 = OrderLine.builder()
                .skuCode("123")
                .quantity(11)
                .price(new BigDecimal(1234))
                .build();
        OrderLine orderLine2 = OrderLine.builder()
                .skuCode("456")
                .quantity(11)
                .price(new BigDecimal(1234))
                .build();
        HashMap map = new HashMap<>();
        map.put("123", orderLine1);
        map.put("456", orderLine2);
        Order order = Order.builder()
                .id(1L)
                .orderDate(LocalDate.now())
                .sendingDate(LocalDate.now())
                .cancellationDate(null)
                .returnDate(null)
                .price(new BigDecimal(123))
                .orderLines(map)
                .build();

        OrderJpa result = instance.mapToOrderJpa(order);

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
    void mapToOrderLineJpa_withNullOrderLine_givesNullOrderLineJpa() {
        OrderMapper instance = new OrderMapperImpl();

        OrderLineJpa result = instance.mapToOrderLineJpa(null);

        assertThat(result).isNull();
    }

    @Test
    void mapToOrderLineJpa_withOrderLine_givesOrderLineJpa() {
        OrderMapper instance = new OrderMapperImpl();
        OrderLine orderLine = OrderLine.builder()
                .skuCode("123")
                .quantity(11)
                .price(BigDecimal.valueOf(11.11))
                .build();

        OrderLineJpa result = instance.mapToOrderLineJpa(orderLine);

        assertThat(result).isNotNull();
        assertThat(result.getSkuCode()).isEqualTo("123");
        assertThat(result.getQuantity()).isEqualTo(11);
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(11.11));
    }
}