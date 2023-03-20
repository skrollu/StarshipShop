package com.starshipshop.inventoryservice.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.starshipshop.inventoryservice.domain.InventoryResponse;
import com.starshipshop.inventoryservice.repository.InventoryRepository;
import com.starshipshop.inventoryservice.service.mapper.InventoryMapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    // TODO
    private final InventoryMapper inventoryMapper;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStockIn(List<String> skuCodes) {
        List<InventoryResponse> result = new ArrayList<>();
        for (String sku: skuCodes) {
            result.add(this.isInStock(sku));
        }
        return result;
    }

    @Transactional(readOnly = true)
    @SneakyThrows
    public InventoryResponse isInStock(String skuCode) {
        log.info("Checking inventory for the following skuCodes: " + skuCode);

        return inventoryRepository.findBySkuCode(skuCode)
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .quantity(inventory.getQuantity()).build())
                .orElse(null);
    }
}
