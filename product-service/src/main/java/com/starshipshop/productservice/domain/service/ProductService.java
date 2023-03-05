package com.starshipshop.productservice.domain.service;

import com.starshipshop.productservice.client.InventoryFeignClient;
import com.starshipshop.productservice.client.StarshipFeignClient;
import com.starshipshop.productservice.client.response.InventoryResponse;
import com.starshipshop.productservice.client.response.StarshipResponse;
import com.starshipshop.productservice.common.exception.ResourceNotFoundException;
import com.starshipshop.productservice.repository.jpa.StarshipProduct;
import com.starshipshop.productservice.repository.jpa.StarshipProductRepository;
import com.starshipshop.productservice.web.response.StarshipProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final InventoryFeignClient inventoryFeignClient;
    private final StarshipFeignClient starshipFeignClient;
    private final StarshipProductRepository starshipProductRepository;

    public StarshipProductResponse getStarshipProduct(String skuCode) {

        StarshipProduct starshipProduct = starshipProductRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Cannot find product with the given skuCode: " + skuCode);
        });
        InventoryResponse inventoryResponse = inventoryFeignClient.isInStock(skuCode);
        StarshipResponse starshipResponse = starshipFeignClient.getStarshipById(starshipProduct.getStarshipId());

        return StarshipProductResponse.builder()
                .name(starshipResponse.getName())
                .description(starshipResponse.getDescription())
                .isInStock(inventoryResponse.isInStock())
                .price(starshipProduct.getPrice())
                .color(starshipProduct.getColor())
                .build();
    }
}
