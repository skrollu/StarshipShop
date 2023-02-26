package com.starshipshop.inventoryservice.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.starshipshop.inventoryservice.domain.InventoryResponse;
import com.starshipshop.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCodes) {
        return inventoryService.isInStockIn(skuCodes);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{skuCode}")
    public InventoryResponse isInStock(@PathVariable String skuCode) {
        return inventoryService.isInStock(skuCode);
    }

}