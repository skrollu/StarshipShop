package com.starshipshop.productservice.adapter;

import com.starshipshop.productservice.adapter.mapper.StarshipProductMapper;
import com.starshipshop.productservice.client.InventoryFeignClient;
import com.starshipshop.productservice.client.StarshipFeignClient;
import com.starshipshop.productservice.client.response.InventoryResponse;
import com.starshipshop.productservice.client.response.StarshipResponse;
import com.starshipshop.productservice.common.exception.ResourceNotFoundException;
import com.starshipshop.productservice.domain.model.StarshipProduct;
import com.starshipshop.productservice.repository.jpa.StarshipProductJpa;
import com.starshipshop.productservice.repository.jpa.StarshipProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class StarshipProductAdapterImpl implements StarshipProductAdapter {
    private final StarshipProductRepository starshipProductRepository;
    private final StarshipProductMapper starshipProductMapper;
    private final InventoryFeignClient inventoryFeignClient;
    private final StarshipFeignClient starshipFeignClient;

    @Override
    public Page<StarshipProduct> findPage(Pageable page) {
        Page<StarshipProductJpa> starshipProductsJpa = starshipProductRepository.findAll(page);

        Set<String> skuCodes = new HashSet<>();
        Set<Long> starshipIds = new HashSet<>();
        starshipProductsJpa.stream().forEach(sp -> {
            skuCodes.add(sp.getSkuCode());
            starshipIds.add(sp.getStarshipId());
        });

        List<InventoryResponse> inventoryResponses = inventoryFeignClient.isInStockIn(skuCodes.stream().toList());
        List<StarshipResponse> starshipResponses = starshipFeignClient.getStarshipByIds(starshipIds.stream().toList())
                .getContent()
                .stream()
                .map(EntityModel::getContent)
                .toList();

        // Gather inventory response list in a map
        Map<String, InventoryResponse> inventorResponseyMap = new HashMap<>();
        for (InventoryResponse i : inventoryResponses) {
            inventorResponseyMap.put(i.getSkuCode(), i);
        }

        // Gather starship response list in a map
        Map<Long, StarshipResponse> starshipResponseMap = new HashMap<>();
        for (StarshipResponse i : starshipResponses) {
            starshipResponseMap.put(i.getId(), i);
        }

        // Gather inventory, starship and product data in a result list.
        List<StarshipProduct> result = new ArrayList<>();
        for (StarshipProductJpa jpa : starshipProductsJpa) {
            InventoryResponse ir = inventorResponseyMap.get(jpa.getSkuCode());
            StarshipResponse sr = starshipResponseMap.get(jpa.getStarshipId());
            if (ir != null && sr != null) {
                StarshipProduct sp = starshipProductMapper.mapToStarshipProduct(sr, ir, jpa);
                result.add(sp);
            }
        }
        return new PageImpl(result, page, page.getPageSize());
    }

    @Override
    public StarshipProduct findBySkuCode(String skuCode) {
        StarshipProductJpa starshipProductJpa = starshipProductRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Cannot find product with the given skuCode: " + skuCode);
                });
        InventoryResponse inventoryResponse = inventoryFeignClient.isInStock(skuCode);
        StarshipResponse starshipResponse = starshipFeignClient.getStarshipById(starshipProductJpa.getStarshipId());

        return starshipProductMapper.mapToStarshipProduct(starshipResponse, inventoryResponse, starshipProductJpa);
    }

}
