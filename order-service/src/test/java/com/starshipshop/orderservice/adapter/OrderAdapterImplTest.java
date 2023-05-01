package com.starshipshop.orderservice.adapter;

import com.starshipshop.orderservice.common.exception.ResourceNotFoundException;
import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.repository.OrderRepository;
import com.starshipshop.orderservice.repository.jpa.OrderJpa;
import com.starshipshop.orderservice.repository.mapper.OrderMapper;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderAdapterImplTest {

    @Test
    void findByOrderNumber_withNullOrderNumber_throwsError() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByUserIdAndOrderNumber(null, null))
                .thenReturn(Optional.empty());
        OrderMapper orderMapper = mock(OrderMapper.class);
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
                () -> instance.findByUserIdAndOrderNumber(null, null));

        assertThat(result.getMessage()).isEqualTo("Cannot find order a blank userId or orderNumber");
    }

    @Test
    void findByOrderNumber_withAnEmptyOrderNumber_throwsError() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByUserIdAndOrderNumber("123", ""))
                .thenReturn(Optional.empty());
        OrderMapper orderMapper = mock(OrderMapper.class);
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
                () -> instance.findByUserIdAndOrderNumber("123", ""));

        assertThat(result.getMessage()).isEqualTo("Cannot find order a blank userId or orderNumber");
    }

    @Test
    void findByOrderNumber_withAnEmptyUserId_givesNothing() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByUserIdAndOrderNumber("", "123"))
                .thenReturn(Optional.empty());
        OrderMapper orderMapper = mock(OrderMapper.class);
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
                () -> instance.findByUserIdAndOrderNumber("", "123"));

        assertThat(result.getMessage()).isEqualTo("Cannot find order a blank userId or orderNumber");
    }

    @Test
    void findByOrderNumber_withValidParameters_givesOrder() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByUserIdAndOrderNumber("123", "123"))
                .thenReturn(Optional.of(OrderJpa.builder().userId("123").orderNumber("123").id(1L)
                        .build()));
        OrderMapper orderMapper = mock(OrderMapper.class);
        when(orderMapper.mapToOrder(OrderJpa.builder()
                .id(1L)
                .userId("123")
                .orderNumber("123")
                .build()))
                .thenReturn(Order.builder()
                        .orderNumber("123")
                        .userId("123")
                        .id(1L)
                        .build());
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        Order result = instance.findByUserIdAndOrderNumber("123", "123");

        assertThat(result).isEqualTo(Order.builder()
                .userId("123")
                .orderNumber("123")
                .id(1L)
                .build());
    }

    @Test
    void findByOrderNumber_withValidParametersButNoResourceFound_throwsError() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByUserIdAndOrderNumber("123", "123"))
                .thenReturn(Optional.empty());
        OrderMapper orderMapper = mock(OrderMapper.class);
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class,
                () -> instance.findByUserIdAndOrderNumber("123", "123"));

        assertThat(result.getMessage()).isEqualTo("No order found with the given orderNumber 123");
    }
}
