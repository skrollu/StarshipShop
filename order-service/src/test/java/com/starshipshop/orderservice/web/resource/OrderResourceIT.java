package com.starshipshop.orderservice.web.resource;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderResourceIT {

    private static final String BASE_URL = "/api/v1/orders";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(value = "123")
    void getOrder_validOrder_givesStatus200() throws Exception {
        mockMvc.perform(get(BASE_URL + "/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber", is("123")))
                .andExpect(jsonPath("$.orderDate").exists())
                .andExpect(jsonPath("$.orderLines.123.price", is(100.0)))
        ;
    }
}
