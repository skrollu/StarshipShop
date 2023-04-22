package com.starshipshop.inventoryservice.service;

import com.starshipshop.inventoryservice.adapter.InventoryAdapter;
import com.starshipshop.inventoryservice.domain.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryAdapter inventoryAdapter;

    @Override
    @Transactional(readOnly = true)
    public List<Inventory> getInventoriesIn(List<String> skuCodes) {
        return inventoryAdapter.findBySkuCodeIn(skuCodes);
    }

    @Override
    @Transactional(readOnly = true)
    public Inventory getInventory(String skuCode) {
        return inventoryAdapter.findBySkuCode(skuCode);
    }
}
