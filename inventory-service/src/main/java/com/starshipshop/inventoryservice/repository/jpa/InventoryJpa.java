package com.starshipshop.inventoryservice.repository.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "inventory")
public class InventoryJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventory", updatable = false, columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "sku_code", updatable = false)
    private String skuCode;

    @Column(name = "quantity", updatable = true)
    private int quantity;
}