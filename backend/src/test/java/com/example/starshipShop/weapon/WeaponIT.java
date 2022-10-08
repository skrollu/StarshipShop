package com.example.starshipShop.weapon;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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

@ActiveProfiles("test-h2")
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WeaponIT {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
    MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    public static final String BASE_URL = "/api/v1/weapons";
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @Order(1)
    @DisplayName("GET all weapons works throught all layers")
    void getWeaponsWorksThroughAllLayers() throws Exception {
        this.mockMvc.perform(get(BASE_URL))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.weapons", hasSize(6)))
        .andExpect(jsonPath("$._embedded.weapons[0].name", is(Matchers.is("WeaponToGet1"))))
        .andExpect(jsonPath("$._embedded.weapons[1].name", is(Matchers.is("WeaponToGet2"))))
        .andExpect(jsonPath("$._embedded.weapons[2].name", is(Matchers.is("WeaponToGet3"))))
        .andExpect(jsonPath("$._embedded.weapons[3].name", is(Matchers.is("WeaponToUpdate4"))))
        // HateOAS links tests
        .andExpect(jsonPath("$._embedded.weapons[0]._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "W5pvAw0r"))))
        .andExpect(jsonPath("$._embedded.weapons[1]._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "Kdp5qpxj"))))
        .andExpect(jsonPath("$._embedded.weapons[2]._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "mbLbXp35"))))
        .andExpect(jsonPath("$._embedded.weapons[3]._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "GELQkpdQ"))))
        .andExpect(jsonPath("$._links.self.href").value(Matchers.endsWith(BASE_URL)));
    }
    
    @Test
    @DisplayName("GET one weapon works throught all layers")
    void getOneWeaponWorksThroughAllLayers() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/{id}", "W5pvAw0r"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.name", is(Matchers.is("WeaponToGet1"))))
        // HateOAS links tests
        .andExpect(jsonPath("$._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "W5pvAw0r"))));
    }
    
    @Test
    @DisplayName("POST a weapon json body works throught all layers")
    void createWeaponWorksThroughAllLayers() throws Exception {        
        
        String json = "{\r\n\"name\": \"WeaponCreated1\",\r\n\"manufacturer\": {\r\n\"id\":\"W5pvAw0r\",\r\n\"name\": \"ManufacturerToGet1\"\r\n}\r\n}";
        mockMvc	.perform(post(BASE_URL)	.contentType(APPLICATION_JSON_UTF8)
        .content(json))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name", is("WeaponCreated1")))
        .andExpect(jsonPath("$.manufacturer").exists())
        .andExpect(jsonPath("$.manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.manufacturer.name", is("ManufacturerToGet1")));
    }
    
    @Test
    @DisplayName("POST a minimal weapon json body works throught all layers")
    void createMinimalWeaponWorksThroughAllLayers() throws Exception {        
        
        String json = "{\r\n\"name\":\"WeaponCreated2\"\r\n}";
        
        mockMvc	.perform(post(BASE_URL)	.contentType(APPLICATION_JSON_UTF8)
        .content(json))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name", is("WeaponCreated2")))
        .andExpect(jsonPath("$.manufacturer", is(nullValue())));
    }
    
    @Test
    @DisplayName("PUT a weapon json body works throught all layers, it removes the manufacturer and update some other data")
    void updateWeaponWorksThroughAllLayers() throws Exception {        
        
        String json = "{\r\n\"name\": \"StarshipUpdated\"\r\n}";
        
        mockMvc	.perform(put(BASE_URL + "/" + "GELQkpdQ")	.contentType(APPLICATION_JSON_UTF8)
        .content(json))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name", is("StarshipUpdated")))
        .andExpect(jsonPath("$.manufacturer", is(nullValue())))
        // HateOAS links tests
        .andExpect(jsonPath("$._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "GELQkpdQ"))));
    }
    
    @Test
    @DisplayName("DEL a weapon works throught all layers")
    void deleteWeaponWorksThroughAllLayers() throws Exception {        
        mockMvc	.perform(delete(BASE_URL + "/{id}", "qZpyYpYM").contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.deleted").exists())
        .andExpect(jsonPath("$.deleted").value(true))
        .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("DEL a weapon throws error")
    void deleteWeaponThrowsError() throws Exception {        
        mockMvc	.perform(delete(BASE_URL + "/{id}", "").contentType(APPLICATION_JSON_UTF8))
        .andExpect(status().is4xxClientError());
    }
}
