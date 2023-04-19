package com.starshipshop.cartservice.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductRequest {
    private String skuCode;
    private int quantity;
}
