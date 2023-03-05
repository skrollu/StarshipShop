package com.starshipshop.productservice.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarshipProductResponse {
    private String name;
    private String description;
    private boolean isInStock;
    private BigDecimal price;
    private String color;
}
