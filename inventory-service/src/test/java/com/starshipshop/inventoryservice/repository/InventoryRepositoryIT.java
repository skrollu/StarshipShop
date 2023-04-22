package com.starshipshop.inventoryservice.repository;

import com.starshipshop.inventoryservice.repository.jpa.InventoryJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class InventoryRepositoryIT {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    void findBySkuCode_withNullSkuCode_giveNothing() {
        String skuCode = null;

        Optional<InventoryJpa> result = inventoryRepository.findBySkuCode(skuCode);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findBySkuCode_withEmptySkuCode_givesNothing() {
        String skuCode = "";

        Optional<InventoryJpa> result = inventoryRepository.findBySkuCode(skuCode);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findBySkuCode_withValidSkuCode_givesInventoryJpaInfo() {
        String skuCode = "123";

        Optional<InventoryJpa> result = inventoryRepository.findBySkuCode(skuCode);

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get().getSkuCode()).isEqualTo("123");
    }

    @Test
    void findBySkuCode_withNonValidSkuCode_givesNothing() {
        String skuCode = "123344";

        Optional<InventoryJpa> result = inventoryRepository.findBySkuCode(skuCode);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findBySkuCodeIn_withEmptyListOfSkuCodes_givesEmptyList() {
        List<String> list = new ArrayList<>();

        List<InventoryJpa> result = inventoryRepository.findBySkuCodeIn(list);

        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findBySkuCodeIn_withValidListOfSkuCodes_givesEmptyList() {
        List<String> list = Arrays.asList("123", "456");

        List<InventoryJpa> result = inventoryRepository.findBySkuCodeIn(list);

        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get(0).getSkuCode()).isEqualTo("123");
        assertThat(result.get(1).getSkuCode()).isEqualTo("456");
    }

    @Test
    void findBySkuCodeIn_withNonValidListOfSkuCodes_givesEmptyList() {
        List<String> list = Arrays.asList("000", "123332");

        List<InventoryJpa> result = inventoryRepository.findBySkuCodeIn(list);

        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isTrue();
    };

    @Test
    void findBySkuCodeIn_withPartialyNonValidListOfSkuCodes_givesEmptyList() {
        List<String> list = Arrays.asList("123", "9999");

        List<InventoryJpa> result = inventoryRepository.findBySkuCodeIn(list);

        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getSkuCode()).isEqualTo("123");
    }
}
