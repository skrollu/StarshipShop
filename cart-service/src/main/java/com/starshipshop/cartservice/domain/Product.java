package com.starshipshop.cartservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Product {
    private Long id;
    private String skuCode;
    private int quantity;
}
