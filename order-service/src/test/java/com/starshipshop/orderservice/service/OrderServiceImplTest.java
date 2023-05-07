package com.starshipshop.orderservice.service;

import com.starshipshop.orderservice.adapter.OrderAdapter;
import com.starshipshop.orderservice.adapter.OrderAdapterImpl;
import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.domain.OrderLine;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Test
    void getOrder_withNullUserId_givesNothing() {
        OrderAdapter orderAdapter = mock(OrderAdapterImpl.class);
        when(orderAdapter.findByUserIdAndOrderNumber(null, 123L))
                .thenReturn(null);
        OrderService instance = new OrderServiceImpl(orderAdapter);

        Order result = instance.getOrder(null, null);

        assertThat(result).isNull();
    }

    @Test
    void getOrder_withNullOrderNumber_givesNothing() {
        OrderAdapter orderAdapter = mock(OrderAdapterImpl.class);
        when(orderAdapter.findByUserIdAndOrderNumber("123", null))
                .thenReturn(null);
        OrderService instance = new OrderServiceImpl(orderAdapter);

        Order result = instance.getOrder(null, null);

        assertThat(result).isNull();
    }

    @Test
    void getOrder_withANullOrderNumber_givesNothing() {
        OrderAdapter orderAdapter = mock(OrderAdapterImpl.class);
        when(orderAdapter.findByUserIdAndOrderNumber("123", null))
                .thenReturn(null);
        OrderService instance = new OrderServiceImpl(orderAdapter);

        Order result = instance.getOrder("123", null);

        assertThat(result).isNull();
    }


    @Test
    void getOrder_withEmptyUserId_givesNothing() {
        OrderAdapter orderAdapter = mock(OrderAdapterImpl.class);
        when(orderAdapter.findByUserIdAndOrderNumber("", 123L))
                .thenReturn(null);
        OrderService instance = new OrderServiceImpl(orderAdapter);

        Order result = instance.getOrder("", 123L);

        assertThat(result).isNull();
    }

    @Test
    void getOrder_withValidOrderNumber_givesOrder() {
        OrderAdapter orderAdapter = mock(OrderAdapterImpl.class);
        when(orderAdapter.findByUserIdAndOrderNumber("123", 123L))
                .thenReturn(Order.builder().id(123L).userId("123").build());
        OrderService instance = new OrderServiceImpl(orderAdapter);

        Order result = instance.getOrder("123", 123L);

        assertThat(result.getId()).isEqualTo(123L);
    }

    @Test
    void createOrder_withNullUserId_throwError() {
        OrderAdapter orderAdapter = mock(OrderAdapterImpl.class);
        OrderService instance = new OrderServiceImpl(orderAdapter);

        NullPointerException result = assertThrows(NullPointerException.class, () -> instance.createOrder(null, null));
    }

    @Test
    void createOrder_withNullOrderLines_throwError() {
        OrderAdapter orderAdapter = mock(OrderAdapterImpl.class);
        OrderService instance = new OrderServiceImpl(orderAdapter);

        NullPointerException result = assertThrows(NullPointerException.class, () -> instance.createOrder("123", null));
    }

    @Test
    void createOrder_withValidParams_givesValidOrder() {
        OrderAdapter orderAdapter = mock(OrderAdapterImpl.class);
        when(orderAdapter.createOrder(any()))
                .thenReturn(Order.builder().userId("123").build());
        HashMap map = new HashMap();
        map.put("123", OrderLine.create("123", BigDecimal.ONE, 11));
        map.put("456", OrderLine.create("456", BigDecimal.TEN, 22));
        OrderService instance = new OrderServiceImpl(orderAdapter);

        Order result = instance.createOrder("123", map);

        assertThat(result.getUserId()).isEqualTo("123");
    }
}