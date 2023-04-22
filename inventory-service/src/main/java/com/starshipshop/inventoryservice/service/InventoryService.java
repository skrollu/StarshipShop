package com.starshipshop.inventoryservice.service;

import com.starshipshop.inventoryservice.domain.Inventory;

import java.util.List;

public interface InventoryService {

    List<Inventory> getInventoriesIn(List<String> skuCodes);
    Inventory getInventory(String skuCode);
}