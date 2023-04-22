package com.starshipshop.inventoryservice.adapter;

import com.starshipshop.inventoryservice.domain.Inventory;
import com.starshipshop.inventoryservice.repository.InventoryRepository;
import com.starshipshop.inventoryservice.repository.jpa.InventoryJpa;
import com.starshipshop.inventoryservice.repository.mapper.InventoryMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InventoryAdapterImplTest {

    @Test
    void findBySkuCodeIn_withNullList_givesEmptyList() {
        InventoryRepository inventoryRepository = mock(InventoryRepository.class);
        when(inventoryRepository.findBySkuCodeIn(null))
                .thenReturn(null);
        InventoryMapper inventoryMapper = mock(InventoryMapper.class);
        InventoryAdapterImpl instance = new InventoryAdapterImpl(inventoryRepository, inventoryMapper);

        List<Inventory> result = instance.findBySkuCodeIn(null);

        assertThat(result.size()).isZero();
    }

    @Test
    void findBySkuCodeIn_withAListOfSkuCodes_givesAListOfInventory() {
        InventoryRepository inventoryRepository = mock(InventoryRepository.class);
        when(inventoryRepository.findBySkuCodeIn(Arrays.asList("123", "456")))
                .thenReturn(Arrays.asList(
                        InventoryJpa.builder().skuCode("123").build(),
                        InventoryJpa.builder().skuCode("456").build()));
        InventoryMapper inventoryMapper = mock(InventoryMapper.class);
        when(inventoryMapper.mapToInventory(InventoryJpa.builder().skuCode("123").build())).thenReturn(Inventory.builder().skuCode("123").build());
        when(inventoryMapper.mapToInventory(InventoryJpa.builder().skuCode("456").build())).thenReturn(Inventory.builder().skuCode("456").build());
        InventoryAdapterImpl instance = new InventoryAdapterImpl(inventoryRepository, inventoryMapper);

        List<Inventory> result = instance.findBySkuCodeIn(Arrays.asList("123", "456"));

        assertThat(result.get(0)).isEqualTo(Inventory.builder().skuCode("123").build());
        assertThat(result.get(1)).isEqualTo(Inventory.builder().skuCode("456").build());
    }

    @Test
    void findBySkuCodeIn_withAListOfSkuCodesMissingInTheRepository_givesAnEmptyListOfInventory() {
        InventoryRepository inventoryRepository = mock(InventoryRepository.class);
        when(inventoryRepository.findBySkuCodeIn(Arrays.asList("123", "456")))
                .thenReturn(Arrays.asList());
        InventoryMapper inventoryMapper = mock(InventoryMapper.class);
        InventoryAdapterImpl instance = new InventoryAdapterImpl(inventoryRepository, inventoryMapper);

        List<Inventory> result = instance.findBySkuCodeIn(Arrays.asList("123", "456"));

        assertThat(result.size()).isZero();
    }

    @Test
    void findBySkuCode_withNullSkuCode_givesNothing() {
        InventoryRepository inventoryRepository = mock(InventoryRepository.class);
        when(inventoryRepository.findBySkuCode(null))
                .thenReturn(Optional.empty());
        InventoryMapper inventoryMapper = mock(InventoryMapper.class);
        InventoryAdapterImpl instance = new InventoryAdapterImpl(inventoryRepository, inventoryMapper);

        Inventory result = instance.findBySkuCode(null);

        assertThat(result).isNull();
    }

    @Test
    void findBySkuCode_withAValidSkuCode_givesInventoryInformation() {
        InventoryRepository inventoryRepository = mock(InventoryRepository.class);
        when(inventoryRepository.findBySkuCode("123"))
                .thenReturn(Optional.of(InventoryJpa.builder().skuCode("123").id(1L).quantity(13).build()));
        InventoryMapper inventoryMapper = mock(InventoryMapper.class);
        when(inventoryMapper.mapToInventory(
                InventoryJpa.builder().skuCode("123").id(1L).quantity(13).build())).
                thenReturn(Inventory.builder().skuCode("123").id(1L).quantity(13).build());
        InventoryAdapterImpl instance = new InventoryAdapterImpl(inventoryRepository, inventoryMapper);

        Inventory result = instance.findBySkuCode("123");

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getSkuCode()).isEqualTo("123");
        assertThat(result.getQuantity()).isEqualTo(13);
    }
}