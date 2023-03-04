package com.starshipshop.productservice.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarshipProductResponse {
    private String name;
    private String description;
    private boolean isInStock;
}
