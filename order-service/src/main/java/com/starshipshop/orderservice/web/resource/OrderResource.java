package com.starshipshop.orderservice.web.resource;

import com.starshipshop.orderservice.domain.Order;
import com.starshipshop.orderservice.service.OrderService;
import com.starshipshop.orderservice.web.mapper.OrderDtoMapper;
import com.starshipshop.orderservice.web.request.CreateOrderInputDto;
import com.starshipshop.orderservice.web.response.OrderOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderResource {

    private final OrderService orderService;
    private final OrderDtoMapper orderDtoMapper;

    @GetMapping("/{orderNumber}")
    ResponseEntity<EntityModel<OrderOutputDto>> getOrder(Principal principal, @PathVariable Long orderNumber) {
        return ResponseEntity.ok(
                EntityModel.of(
                        orderDtoMapper.mapOrderToOrderOutputDto(
                                orderService.getOrder(principal.getName(), orderNumber)
                        )));
    }

    // TODO add status
    @PostMapping
    public EntityModel<OrderOutputDto> createOrder(Principal principal, @RequestBody @Valid CreateOrderInputDto coid) {
        Order order = orderService.createOrder(principal.getName(), orderDtoMapper.mapSetOfOrderLinesToHashMap(coid.getOrderLines()));
        return EntityModel.of(orderDtoMapper.mapOrderToOrderOutputDto(order));
    }
}