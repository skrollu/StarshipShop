package com.starshipshop.inventoryservice.repository;

import com.starshipshop.inventoryservice.repository.jpa.InventoryJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryJpa, Long> {

    List<InventoryJpa> findBySkuCodeIn(List<String> skuCodes);
    Optional<InventoryJpa> findBySkuCode(String skuCode);

}
