package com.starshipshop.orderservice.web.resource;

import com.starshipshop.orderservice.service.OrderService;
import com.starshipshop.orderservice.web.mapper.OrderDtoMapper;
import com.starshipshop.orderservice.web.response.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderResource {

    private final OrderService orderService;
    private final OrderDtoMapper orderDtoMapper;

    @GetMapping("/{orderNumber}")
    ResponseEntity<EntityModel<OrderDto>> getOrder(Principal principal, @PathVariable String orderNumber) {
        return ResponseEntity.ok(
                EntityModel.of(
                        orderDtoMapper.mapOrderToOrderDto(
                                orderService.getOrder(principal.getName(), orderNumber)
                        )));
    }

    //private final OrderAssembler assembler;
/*
    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<EntityModel<OrderDto>> createOrder(@RequestBody @Valid CreateOrderInputDto coid) {
        EntityModel<OrderDto> entityModel = assembler.toModel(this.orderService.createOrder(coid));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
                .toUri())
                .body(entityModel);
    }
    */
}