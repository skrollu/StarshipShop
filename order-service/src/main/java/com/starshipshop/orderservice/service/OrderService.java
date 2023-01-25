package com.starshipshop.orderservice.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.starshipshop.orderservice.domain.CreateOrderInputDto;
import com.starshipshop.orderservice.domain.OrderDto;
import com.starshipshop.orderservice.repository.OrderLineRepository;
import com.starshipshop.orderservice.repository.OrderRepository;
import com.starshipshop.orderservice.repository.jpa.order.Order;
import com.starshipshop.orderservice.repository.jpa.order.OrderLine;
import com.starshipshop.orderservice.service.mapper.OrderMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderDto createOrder(@Valid CreateOrderInputDto coid) {
        Order toSave = orderMapper.mapCreateOrderInputDtoToOrder(coid);
        toSave.setOrderNumber("test");
        toSave.setOrderDate(LocalDate.now());
        BigDecimal price = new BigDecimal(0);
        for (OrderLine o : toSave.getOrderLines()) {
            o.setOrder(toSave);
            price = price.add(new BigDecimal(o.getPrice().doubleValue()).multiply(new BigDecimal(o.getQuantity())));
        }
        log.info("Calculated price: ", price.toString());
        toSave.setPrice(price);
        Order order = orderRepository.save(toSave);
        order.setOrderLines(saveOrderLines(toSave.getOrderLines()));
        return orderMapper.mapOrderToOrderDto(order);
    }

    private Set<OrderLine> saveOrderLines(Set<OrderLine> orderLines) {
        Set<OrderLine> result = new HashSet<>();
        for (OrderLine o : orderLines) {
            result.add(orderLineRepository.save(o));
        }
        return result;
    }

}
