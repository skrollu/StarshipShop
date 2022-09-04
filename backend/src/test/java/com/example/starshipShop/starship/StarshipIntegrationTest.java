package com.example.starshipShop.starship;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.example.starshipShop.dto.ManufacturerRequestInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@ActiveProfiles("test-h2")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StarshipIntegrationTest {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
    MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    public static final String BASE_URL = "/api/v1/starships";
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @DisplayName("GET all starships works throught all layers")
    void getStarshipsWorksThroughAllLayers() throws Exception {
        this.mockMvc.perform(get(BASE_URL))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.starships", hasSize(2)))
        .andExpect(jsonPath("$._embedded.starships[0].name", is(Matchers.is("Starship1"))))
        .andExpect(jsonPath("$._embedded.starships[1].name", is(Matchers.is("Starship2"))))
        // HateOAS links tests
        .andExpect(jsonPath("$._embedded.starships[0]._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "W5pvAw0r"))))
        .andExpect(jsonPath("$._embedded.starships[1]._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "Kdp5qpxj"))))
        .andExpect(jsonPath("$._links.self.href").value(Matchers.endsWith(BASE_URL)));
    }
    
    @Test
    @DisplayName("GET one starship works throught all layers And check response is complete")
    void getStarshipByIdWorksThroughAllLayersAndCheckForACompleteBody() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/{id}", "W5pvAw0r"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.name", is("Starship1")))
        .andExpect(jsonPath("$.engines", is("engine1")))
        .andExpect(jsonPath("$.description", is("description1")))
        .andExpect(jsonPath("$.height", is(1.0)))
        .andExpect(jsonPath("$.width", is(2.0)))
        .andExpect(jsonPath("$.lenght", is(3.0)))
        .andExpect(jsonPath("$.weight", is(4.5)))
        .andExpect(jsonPath("$.manufacturer").exists())
        .andExpect(jsonPath("$.manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.manufacturer.name", is("Manufacturer1")))
        .andExpect(jsonPath("$.hyperdriveSystem.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.hyperdriveSystem.name", is("Hyperdrive1")))
        .andExpect(jsonPath("$.hyperdriveSystem.manufacturer").exists())
        .andExpect(jsonPath("$.hyperdriveSystem.manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.hyperdriveSystem.manufacturer.name", is("Manufacturer1")))
        .andExpect(jsonPath("$.weapons").exists())
        .andExpect(jsonPath("$.weapons", hasSize(3)))
        .andExpect(jsonPath("$.weapons[0].id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.weapons[0].name", is("Weapon1")))
        .andExpect(jsonPath("$.weapons[0].manufacturer").exists())
        .andExpect(jsonPath("$.weapons[0].manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.weapons[0].manufacturer.name", is("Manufacturer1")))
        .andExpect(jsonPath("$.weapons[1].id", is("Kdp5qpxj")))
        .andExpect(jsonPath("$.weapons[1].name", is("Weapon2")))
        .andExpect(jsonPath("$.weapons[1].manufacturer").exists())
        .andExpect(jsonPath("$.weapons[1].manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.weapons[1].manufacturer.name", is("Manufacturer1")))
        .andExpect(jsonPath("$.weapons[2].id", is("mbLbXp35")))
        .andExpect(jsonPath("$.weapons[2].name", is("Weapon3")))
        .andExpect(jsonPath("$.weapons[2].manufacturer").exists())
        .andExpect(jsonPath("$.weapons[2].manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.weapons[2].manufacturer.name", is("Manufacturer1")))
        .andExpect(jsonPath("$._links.self.href", is(Matchers.endsWith(BASE_URL + "/" + "W5pvAw0r"))))
        .andExpect(jsonPath("$._links.starships.href").value(Matchers.endsWith(BASE_URL)));
    }
}
