package com.starshipshop.orderservice.controller;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starshipshop.orderservice.controller.advice.OrderAssembler;
import com.starshipshop.orderservice.domain.CreateOrderInputDto;
import com.starshipshop.orderservice.domain.OrderDto;
import com.starshipshop.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderAssembler assembler;

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<EntityModel<OrderDto>> createOrder(@RequestBody @Valid CreateOrderInputDto coid) {
        EntityModel<OrderDto> entityModel = assembler.toModel(this.orderService.createOrder(coid));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
                .toUri())
                .body(entityModel);
    }
}