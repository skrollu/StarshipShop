package com.starshipshop.cartservice.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String skuCode;
    private int quantity;
}
