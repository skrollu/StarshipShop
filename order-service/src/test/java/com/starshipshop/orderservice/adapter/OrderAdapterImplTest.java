package com.starshipshop.orderservice.adapter;

import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.repository.OrderRepository;
import com.starshipshop.orderservice.repository.jpa.OrderJpa;
import com.starshipshop.orderservice.repository.mapper.OrderMapper;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderAdapterImplTest {

    @Test
    void findByOrderNumber_withNullOrderNumber_givesNothing() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByOrderNumber(null))
                .thenReturn(Optional.empty());
        OrderMapper orderMapper = mock(OrderMapper.class);
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        Order result = instance.findByOrderNumber(null);

        assertThat(result).isNull();
    }

    @Test
    void findByOrderNumber_withAnEmptyOrderNumber_givesNothing() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByOrderNumber(""))
                .thenReturn(Optional.empty());
        OrderMapper orderMapper = mock(OrderMapper.class);
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        Order result = instance.findByOrderNumber("");

        assertThat(result).isNull();
    }

    @Test
    void findByOrderNumber_withAValidOrderNumber_givesOrder() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByOrderNumber("123"))
                .thenReturn(Optional.of(OrderJpa.builder().orderNumber("123").id(1L)
                        .build()));
        OrderMapper orderMapper = mock(OrderMapper.class);
        when(orderMapper.mapToOrder(OrderJpa.builder()
                .id(1L)
                .orderNumber("123")
                .build()))
                .thenReturn(Order.builder()
                        .orderNumber("123")
                        .id(1L)
                        .build());
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        Order result = instance.findByOrderNumber("123");

        assertThat(result).isEqualTo(Order.builder()
                .orderNumber("123")
                .id(1L)
                .build());
    }
}
