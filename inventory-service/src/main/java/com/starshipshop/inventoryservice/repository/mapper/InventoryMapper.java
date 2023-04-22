package com.starshipshop.inventoryservice.repository.mapper;

import com.starshipshop.inventoryservice.domain.Inventory;
import com.starshipshop.inventoryservice.repository.jpa.InventoryJpa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface InventoryMapper {

    Inventory mapToInventory (InventoryJpa inventoryJpa);
    InventoryJpa mapToInventoryJpa (Inventory inventory);
}
