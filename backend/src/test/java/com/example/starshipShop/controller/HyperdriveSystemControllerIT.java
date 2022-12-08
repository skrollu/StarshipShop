package com.example.starshipshop.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

@ActiveProfiles("test-mysql")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HyperdriveSystemControllerIT {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private static final String BASE_URL = "/api/v1/hyperdriveSystems";
    private static final String USER_USERNAME = "user@mail.com";
    private static final String USER_PASSWORD = "password";
    private static final String ADMIN_USERNAME = "admin@mail.com";
    private static final String ADMIN_PASSWORD = "password";

    @Autowired
    private MockMvc mockMvc;

    // GET /hyperdriveSystems
    @Test
    @Order(1)
    @Tag("GET /hyperdriveSystems")
    @DisplayName("GET all hyperdriveSystems works throught all layers")
    void getHyperdriveSystemsWorksThroughAllLayers() throws Exception {
        this.mockMvc.perform(get(BASE_URL)).andDo(log()).andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.hyperdriveSystems", hasSize(4)))
                .andExpect(content().string(containsString("HyperdriveToGet1")))
                .andExpect(content().string(containsString("HyperdriveToGet2")))
                .andExpect(jsonPath("$._embedded.hyperdriveSystems[0]").exists())
                .andExpect(jsonPath("$._embedded.hyperdriveSystems[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").value(Matchers.endsWith(BASE_URL)));
    }

    // GET /hyperdriveSystems/{id}
    @Test
    @Order(1)
    @Tag("GET /hyperdriveSystems/{id}")
    @DisplayName("GET one hyperdriveSystem works throught all layers")
    void getOneHyperdriveSystemWorksThroughAllLayers() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/{id}", "W5pvAw0r"))

                .andExpect(status().isOk()).andExpect(jsonPath("$.id", is("W5pvAw0r")))
                .andExpect(jsonPath("$.name", is(Matchers.is("HyperdriveToGet1"))))
                // HateOAS links tests
                .andExpect(jsonPath("$._links.self.href",
                        is(Matchers.endsWith(BASE_URL + "/" + "W5pvAw0r"))));
    }

    // POST /hyperdriveSystems
    @Test
    @Tag("POST /hyperdriveSystems")
    @DisplayName("POST a hyperdriveSystem json body when authenticated as Admin should works throught all layers")
    void createMinimalHyperdriveSystem_whenAuthenticatedAsAdmin_shouldWorksThroughAllLayers()
            throws Exception {

        String json = "{\r\n\"name\":\"HyperdriveSystemCreated\"\r\n}";

        mockMvc.perform(post(BASE_URL).with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON_UTF8).content(json))

                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("HyperdriveSystemCreated")))
                .andExpect(jsonPath("$.manufacturer", is(nullValue())))
                // HateOAS links tests
                .andExpect(jsonPath("$._links.hyperdriveSystems.href")
                        .value(Matchers.endsWith(BASE_URL)));
    }

    // PUT /hyperdriveSystems/{id}
    @Test
    @Tag("PUT /hyperdriveSystems/{id}")
    @DisplayName("PUT a hyperdriveSystem json body when authenticated as Admin should works throught all layers, it removes the manufacturer and update some other data")
    void updateHyperdriveSystem_whenAuthenticatedAsAdmin_shouldWorksThroughAllLayers()
            throws Exception {

        String json = "{\r\n\"name\": \"HyperdriveUpdated3\"\r\n}";

        mockMvc.perform(
                put(BASE_URL + "/" + "mbLbXp35").with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                        .contentType(APPLICATION_JSON_UTF8).content(json))

                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("HyperdriveUpdated3")))
                .andExpect(jsonPath("$.manufacturer", is(nullValue())))
                // HateOAS links tests
                .andExpect(jsonPath("$._links.self.href",
                        is(Matchers.endsWith(BASE_URL + "/" + "mbLbXp35"))));
    }

    // DELETE /hyperdriveSystems/{id}
    @Test
    @Tag("DELETE /hyperdriveSystems/{id}")
    @DisplayName("DELETE a hyperdriveSystem when authenticated as Admin should works throught all layers")
    void deleteHyperdriveSystem_whenAuthenticatedAsAdmin_shouldWorksThroughAllLayers()
            throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{id}", "GELQkpdQ")
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)).contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.deleted").value(true)).andExpect(status().isOk());
    }

    @Test
    @Tag("DELETE /hyperdriveSystems/{id}")
    @DisplayName("DELETE a hyperdriveSystem with no id when authenticated as Admin should throws client error because this endpoint doesn't exist")
    void deleteHyperdriveSystem_whenAuthenticatedAsAdmin_shouldThrowsClientError()
            throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{id}", "")
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError());
    }
}
