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
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final InventoryFeignClient inventoryFeignClient;
    private final StarshipFeignClient starshipFeignClient;
    private final StarshipProductRepository starshipProductRepository;

    public List<StarshipProductResponse> getAllStarshipProduct() {
        List<StarshipProduct> starshipProducts = starshipProductRepository.findAll();

        List<String> skuCodes = new ArrayList<>();
        starshipProducts.stream().forEach(sp -> skuCodes.add(sp.getSkuCode()));
        List<String> starshipIds = new ArrayList<>();
        starshipProducts.stream().forEach(sp -> starshipIds.add(sp.getStarshipId()));

        List<InventoryResponse> inventoryResponses = inventoryFeignClient.isInStockIn(skuCodes);
        List<StarshipResponse> starshipResponses = starshipFeignClient.getStarshipByIds(starshipIds)
                .getContent()
                .stream()
                .map(EntityModel::getContent)
                .toList();

        Map<String, InventoryResponse> map1 = new HashMap<>();
        for (InventoryResponse i : inventoryResponses) {
            map1.put(i.getSkuCode(), i);
        }

        Map<String, StarshipResponse> map2 = new HashMap<>();
        for (StarshipResponse i : starshipResponses) {
            map2.put(i.getId(), i);
        }

        List<StarshipProductResponse> result = new ArrayList<>();
        for (StarshipProduct i : starshipProducts) {
            InventoryResponse ir = map1.get(i.getSkuCode());
            StarshipResponse sr = map2.get(i.getStarshipId());
            if (ir != null && sr != null) {
                StarshipProductResponse spr = StarshipProductResponse.builder()
                        .name(sr.getName())
                        .description(sr.getDescription())
                        .quantity(ir.getQuantity())
                        .price(i.getPrice())
                        .color(i.getColor())
                        .build();
                result.add(spr);
            }
        }

        return result;
    }

    public StarshipProductResponse getStarshipProductBySkuCode(String skuCode) {

        StarshipProduct starshipProduct = starshipProductRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Cannot find product with the given skuCode: " + skuCode);
        });
        InventoryResponse inventoryResponse = inventoryFeignClient.isInStock(skuCode);
        StarshipResponse starshipResponse = starshipFeignClient.getStarshipById(starshipProduct.getStarshipId());

        return StarshipProductResponse.builder()
                .name(starshipResponse.getName())
                .description(starshipResponse.getDescription())
                .quantity(inventoryResponse.getQuantity())
                .price(starshipProduct.getPrice())
                .color(starshipProduct.getColor())
                .build();
    }
}
