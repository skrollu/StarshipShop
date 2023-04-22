package com.starshipshop.inventoryservice.repository.mapper;

import com.starshipshop.inventoryservice.domain.Inventory;
import com.starshipshop.inventoryservice.repository.jpa.InventoryJpa;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InventoryMapperTest {

    @Test
    void mapToInventory_nullObject_givesEmptyInventory() {
        InventoryMapper instance = new InventoryMapperImpl();

        Inventory result = instance.mapToInventory(null);

        assertThat(result).isNull();
    }

    @Test
    void mapToInventory_validObject_givesInventoryInformations() {
        InventoryMapper instance = new InventoryMapperImpl();
        InventoryJpa jpa = InventoryJpa.builder()
                .id(1L)
                .quantity(0)
                .skuCode("2134")
                .build();

        Inventory result = instance.mapToInventory(jpa);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getQuantity()).isEqualTo(0);
        assertThat(result.getSkuCode()).isEqualTo("2134");
    }

    @Test
    void mapToInventoryJpa_nullObject_givesNullInventory() {
        InventoryMapper instance = new InventoryMapperImpl();

        InventoryJpa result = instance.mapToInventoryJpa(null);

        assertThat(result).isNull();
    }

    @Test
    void mapToInventoryJpa_validObject_givesInventoryInformations() {
        InventoryMapper instance = new InventoryMapperImpl();
        Inventory inventory = Inventory.builder().id(1L).quantity(0).skuCode(null).build();

        InventoryJpa result = instance.mapToInventoryJpa(inventory);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getQuantity()).isEqualTo(0);
        assertThat(result.getSkuCode()).isEqualTo(null);
    }
}
