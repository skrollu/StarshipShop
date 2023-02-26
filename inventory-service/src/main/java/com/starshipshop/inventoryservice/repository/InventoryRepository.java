package com.starshipshop.inventoryservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starshipshop.inventoryservice.repository.jpa.inventory.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findBySkuCodeIn(List<String> skuCodes);
    Optional<Inventory> findBySkuCode(String skuCode);

}
