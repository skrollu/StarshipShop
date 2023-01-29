package com.starshipshop.inventoryservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.starshipshop.inventoryservice.repository.InventoryRepository;
import com.starshipshop.inventoryservice.repository.jpa.inventory.Inventory;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test-mysql")
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InventoryControllerIT {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private static final String BASE_URL = "/api/v1/inventory";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    void isInStock_shouldWorks() throws Exception {

        ResultActions res = mockMvc.perform(
                get(BASE_URL + "?skuCodes=123,456,789").contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].skuCode", is("123")))
                .andExpect(jsonPath("$[0].inStock", is(true)))
                .andExpect(jsonPath("$[1].skuCode", is("456")))
                .andExpect(jsonPath("$[1].inStock", is(true)))
                .andExpect(jsonPath("$[2].skuCode", is("789")))
                .andExpect(jsonPath("$[2].inStock", is(false)));

        List<Inventory> inventory = inventoryRepository.findBySkuCodeIn(List.of("123", "456", "789"));
        assertEquals(3, inventory.size());
        assertEquals(true, inventory.get(0).getQuantity() > 0);
        assertEquals(true, inventory.get(1).getQuantity() > 0);
        assertEquals(false, inventory.get(2).getQuantity() > 0);

    }
}
