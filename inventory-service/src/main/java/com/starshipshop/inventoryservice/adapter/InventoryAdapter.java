package com.starshipshop.inventoryservice.adapter;

import com.starshipshop.inventoryservice.domain.Inventory;

import java.util.List;

public interface InventoryAdapter {
    List<Inventory> findBySkuCodeIn(List<String> skuCodes);
    Inventory findBySkuCode(String skuCode);
}

