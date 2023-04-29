package com.starshipshop.orderservice.web.resource;

import com.starshipshop.orderservice.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderResource {

    private final OrderServiceImpl orderService;
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