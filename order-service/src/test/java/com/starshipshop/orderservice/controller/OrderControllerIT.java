package com.starshipshop.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test-mysql")
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerIT {

    /*
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private static final String BASE_URL = "/api/v1/orders";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    void createOrder_shouldWorks() throws Exception {
        String json = "{\r\n    \"orderLines\": [\r\n        {\r\n            \"skuCode\": \"123\",\r\n            \"price\": 20,\r\n            \"quantity\": 2\r\n        },\r\n        {\r\n            \"skuCode\": \"12993\",\r\n            \"price\": 20,\r\n            \"quantity\": 5\r\n        }\r\n    ]\r\n}";

        ResultActions res = mockMvc.perform(
                post(BASE_URL).contentType(APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderDate").exists());
        Order order = orderRepository.findByOrderNumber("test");
        assertEquals(new BigDecimal("140.00"), order.getPrice());
    }
    */
}
