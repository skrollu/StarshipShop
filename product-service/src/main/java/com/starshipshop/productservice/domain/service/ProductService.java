package com.starshipshop.productservice.domain.service;

import com.starshipshop.productservice.client.InventoryFeignClient;
import com.starshipshop.productservice.client.request.InventoryResponse;
import com.starshipshop.productservice.web.response.StarshipProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final InventoryFeignClient inventoryFeignClient;

    public StarshipProductResponse getStarshipProduct(String skuCode) {

        InventoryResponse inventoryResponse = inventoryFeignClient.isInStock(skuCode);
        StarshipProductResponse result = StarshipProductResponse.builder()
                .name(null)
                .description(null)
                .isInStock(inventoryResponse.isInStock())
                .build();

        return result;
    }
}
