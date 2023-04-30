package com.starshipshop.orderservice.service;

import com.starshipshop.orderservice.adapter.OrderAdapter;
import com.starshipshop.orderservice.adapter.OrderAdapterImpl;
import com.starshipshop.orderservice.domain.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Test
    void getOrder_withNullUserId_givesNothing() {
        OrderAdapter orderAdapter = mock(OrderAdapterImpl.class);
        when(orderAdapter.findByUserIdAndOrderNumber(null, "123"))
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
    void getOrder_withEmptyOrderNumber_givesNothing() {
        OrderAdapter orderAdapter = mock(OrderAdapterImpl.class);
        when(orderAdapter.findByUserIdAndOrderNumber("123", ""))
                .thenReturn(null);
        OrderService instance = new OrderServiceImpl(orderAdapter);

        Order result = instance.getOrder("123", "");

        assertThat(result).isNull();
    }


    @Test
    void getOrder_withEmptUserId_givesNothing() {
        OrderAdapter orderAdapter = mock(OrderAdapterImpl.class);
        when(orderAdapter.findByUserIdAndOrderNumber("", "123"))
                .thenReturn(null);
        OrderService instance = new OrderServiceImpl(orderAdapter);

        Order result = instance.getOrder("", "123");

        assertThat(result).isNull();
    }

    @Test
    void getOrder_withValidOrderNumber_givesOrder() {
        OrderAdapter orderAdapter = mock(OrderAdapterImpl.class);
        when(orderAdapter.findByUserIdAndOrderNumber("123", "123"))
                .thenReturn(Order.builder().userId("123").orderNumber("123").build());
        OrderService instance = new OrderServiceImpl(orderAdapter);

        Order result = instance.getOrder("123", "123");

        assertThat(result.getOrderNumber()).isEqualTo("123");
    }
}