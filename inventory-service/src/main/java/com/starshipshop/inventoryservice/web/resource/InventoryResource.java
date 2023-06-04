package com.starshipshop.inventoryservice.web.resource;

import com.starshipshop.inventoryservice.domain.Inventory;
import com.starshipshop.inventoryservice.service.InventoryService;
import com.starshipshop.inventoryservice.web.mapper.InventoryDtoMapper;
import com.starshipshop.inventoryservice.web.response.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryResource {

    private final InventoryService inventoryService;
    private final InventoryDtoMapper inventoryDtoMapper;

    @PreAuthorize("permitAll()")
    @GetMapping("/{skuCode}")
    InventoryResponse getInventory(@PathVariable String skuCode) {
        Inventory inventory = inventoryService.getInventory(skuCode);
        return inventoryDtoMapper.mapToInventoryResponse(inventory);
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    List<InventoryResponse> getInventoriesIn(@RequestParam List<String> skuCodes) {
        return inventoryService.getInventoriesIn(skuCodes).stream().map(inventoryDtoMapper::mapToInventoryResponse).toList();
    }
}