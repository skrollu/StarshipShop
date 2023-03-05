package com.starshipshop.inventoryservice.controller;

import com.starshipshop.inventoryservice.domain.InventoryResponse;
import com.starshipshop.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

//    @PreAuthorize("permitAll()")
    @GetMapping("/{skuCode}")
    public InventoryResponse isInStock(@PathVariable String skuCode) {
        return inventoryService.isInStock(skuCode);
    }

//    @PreAuthorize("permitAll()")
    @GetMapping
    public List<InventoryResponse> isInStockIn(@RequestParam List<String> skuCodes) {
        return inventoryService.isInStockIn(skuCodes);
    }
}