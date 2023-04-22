package com.starshipshop.inventoryservice.adapter;

import com.starshipshop.inventoryservice.domain.Inventory;
import com.starshipshop.inventoryservice.repository.InventoryRepository;
import com.starshipshop.inventoryservice.repository.jpa.InventoryJpa;
import com.starshipshop.inventoryservice.repository.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryAdapterImpl implements InventoryAdapter {

    final InventoryRepository inventoryRepository;
    final InventoryMapper inventoryMapper;

    /**
     * @param skuCodes
     * @return return a list containing Inventory object corresponding to the given skuCodes, or an empty list. Some non present skuCodes may miss.
     */
    @Override
    public List<Inventory> findBySkuCodeIn(List<String> skuCodes) {
        List<InventoryJpa> result = inventoryRepository.findBySkuCodeIn(skuCodes);
        return CollectionUtils.emptyIfNull(result).stream().map(inventoryMapper::mapToInventory).toList();
    }

    @Override
    public Inventory findBySkuCode(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode)
                .map(inventoryMapper::mapToInventory)
                .orElse(null);
    }
}
