package com.starshipshop.productservice.domain.service;

import com.starshipshop.productservice.client.InventoryFeignClient;
import com.starshipshop.productservice.client.StarshipFeignClient;
import com.starshipshop.productservice.client.response.InventoryResponse;
import com.starshipshop.productservice.client.response.StarshipResponse;
import com.starshipshop.productservice.web.response.StarshipProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final InventoryFeignClient inventoryFeignClient;
    private final StarshipFeignClient starshipFeignClient;

    public StarshipProductResponse getStarshipProduct(String skuCode) {

        InventoryResponse inventoryResponse = inventoryFeignClient.isInStock(skuCode);
        StarshipResponse starshipResponse = starshipFeignClient.getStarshipById("npw2GoM6");

        return StarshipProductResponse.builder()
                .name(starshipResponse.getName())
                .description(starshipResponse.getDescription())
                .isInStock(inventoryResponse.isInStock())
                .build();
    }
}
