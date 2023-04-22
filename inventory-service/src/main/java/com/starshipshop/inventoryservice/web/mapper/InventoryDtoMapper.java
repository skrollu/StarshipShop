package com.starshipshop.inventoryservice.web.mapper;

import com.starshipshop.inventoryservice.domain.Inventory;
import com.starshipshop.inventoryservice.web.response.InventoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface InventoryDtoMapper {

    InventoryResponse mapToInventoryResponse(Inventory inventory);
}
