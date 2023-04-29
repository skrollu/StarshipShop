package com.starshipshop.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
/*
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderDto createOrder(@Valid CreateOrderInputDto coid) {
        Order toSave = orderMapper.mapCreateOrderInputDtoToOrder(coid);
        toSave.setOrderNumber("test");
        toSave.setOrderDate(LocalDate.now());
        BigDecimal price = new BigDecimal(0);
        for (OrderLineJpa o : toSave.getOrderLines()) {
            o.setOrder(toSave);
            price = price.add(new BigDecimal(o.getPrice().doubleValue()).multiply(new BigDecimal(o.getQuantity())));
        }
        log.info("Calculated price: ", price.toString());
        toSave.setPrice(price);
        Order order = orderRepository.save(toSave);
        order.setOrderLines(saveOrderLines(toSave.getOrderLines()));
        return orderMapper.mapOrderToOrderDto(order);
    }

    private Set<OrderLineJpa> saveOrderLines(Set<OrderLineJpa> orderLines) {
        Set<OrderLineJpa> result = new HashSet<>();
        for (OrderLineJpa o : orderLines) {
            result.add(orderLineRepository.save(o));
        }
        return result;
    }
*/
}
