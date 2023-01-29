package com.starshipshop.inventoryservice.repository.jpa.inventory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventory", updatable = false, columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "sku_code", updatable = false)
    private String skuCode;

    @Column(name = "quantity", updatable = true)
    private Integer quantity;
}