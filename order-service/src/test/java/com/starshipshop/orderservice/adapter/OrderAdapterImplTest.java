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
import static org.mockito.Mockito.*;

public class OrderAdapterImplTest {

    @Test
    void findByOrderNumber_withNullOrderNumber_throwsError() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByUserIdAndId(null, null))
                .thenReturn(Optional.empty());
        OrderMapper orderMapper = mock(OrderMapper.class);
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        NullPointerException result = assertThrows(NullPointerException.class,
                () -> instance.findByUserIdAndOrderNumber(null, null));
    }

    @Test
    void findByOrderNumber_withANullOrderNumber_throwsError() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByUserIdAndId("123", null))
                .thenReturn(Optional.empty());
        OrderMapper orderMapper = mock(OrderMapper.class);
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        NullPointerException result = assertThrows(NullPointerException.class,
                () -> instance.findByUserIdAndOrderNumber("123", null));
    }

    @Test
    void findByOrderNumber_withAnEmptyUserId_givesNothing() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByUserIdAndId("", 123L))
                .thenReturn(Optional.empty());
        OrderMapper orderMapper = mock(OrderMapper.class);
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
                () -> instance.findByUserIdAndOrderNumber("", 123L));

        assertThat(result.getMessage()).isEqualTo("Cannot find order with a blank userId or orderNumber");
    }

    @Test
    void findByOrderNumber_withValidParameters_givesOrder() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByUserIdAndId("123", 123L))
                .thenReturn(Optional.of(OrderJpa.builder().userId("123").id(123L)
                        .build()));
        OrderMapper orderMapper = mock(OrderMapper.class);
        when(orderMapper.mapToOrder(OrderJpa.builder()
                .id(123L)
                .userId("123")
                .build()))
                .thenReturn(Order.builder()
                        .userId("123")
                        .id(123L)
                        .build());
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        Order result = instance.findByUserIdAndOrderNumber("123", 123L);

        assertThat(result).isEqualTo(Order.builder()
                .userId("123")
                .id(123L)
                .build());
    }

    @Test
    void findByOrderNumber_withValidParametersButNoResourceFound_throwsError() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByUserIdAndId("123", 123L))
                .thenReturn(Optional.empty());
        OrderMapper orderMapper = mock(OrderMapper.class);
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class,
                () -> instance.findByUserIdAndOrderNumber("123", 123L));

        assertThat(result.getMessage()).isEqualTo("No order found with the given orderNumber 123");
    }

    @Test
    void createOrder_withNullOrder_throwsError() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        OrderMapper orderMapper = mock(OrderMapper.class);
        OrderAdapterImpl instance = new OrderAdapterImpl(orderRepository, orderMapper);

        NullPointerException result = assertThrows(NullPointerException.class,
                () -> instance.createOrder(null));
    }
}
