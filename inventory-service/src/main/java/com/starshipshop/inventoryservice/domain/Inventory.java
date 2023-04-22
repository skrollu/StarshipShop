package com.starshipshop.inventoryservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    private Long id;
    private String skuCode;
    private int quantity;
}
