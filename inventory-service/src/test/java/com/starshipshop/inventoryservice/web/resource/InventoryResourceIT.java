package com.starshipshop.inventoryservice.web.resource;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.web.servlet.MvcResult;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InventoryResourceIT {
    private static final String BASE_URL = "/api/v1/inventory";

    @Autowired
    MockMvc mockMvc;

    @Test
    void getInventory_validSkuCode_givesInventory() throws Exception {
        mockMvc.perform(get(BASE_URL + "/123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skuCode", is("123456789")))
                .andExpect(jsonPath("$.quantity", is(123)))
        ;
    }

    @Test
    void getInventoriesIn_validSkuCodes_givesInventories() throws Exception {
        mockMvc.perform(get(BASE_URL + "?skuCodes=123456789,123456788,123456787"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].skuCode", is("123456789")))
                .andExpect(jsonPath("$[0].quantity", is(123)))
        ;
    }
}