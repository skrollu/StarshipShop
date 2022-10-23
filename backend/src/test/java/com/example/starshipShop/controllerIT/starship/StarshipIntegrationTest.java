package com.example.starshipShop.controllerIT.starship;


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
public class StarshipIntegrationTest {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
    MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    public static final String BASE_URL = "/api/v1/starships";
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @Order(1)
    @DisplayName("GET all starships works throught all layers")
    void getStarshipsWorksThroughAllLayers() throws Exception {
        this.mockMvc.perform(get(BASE_URL))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.starships", hasSize(4)))
        .andExpect(jsonPath("$._embedded.starships[0].name", is(Matchers.is("StarshipToGet1"))))
        .andExpect(jsonPath("$._embedded.starships[1].name", is(Matchers.is("StarshipToGet2"))))
        .andExpect(jsonPath("$._embedded.starships[2].name", is(Matchers.is("StarshipToUpdate1"))))
        .andExpect(jsonPath("$._embedded.starships[3].name", is(Matchers.is("StarshipToDelete1"))))
        // HateOAS links tests
        .andExpect(jsonPath("$._embedded.starships[0]._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "W5pvAw0r"))))
        .andExpect(jsonPath("$._embedded.starships[1]._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "Kdp5qpxj"))))
        .andExpect(jsonPath("$._embedded.starships[2]._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "mbLbXp35"))))
        .andExpect(jsonPath("$._embedded.starships[3]._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "GELQkpdQ"))))
        .andExpect(jsonPath("$._links.self.href").value(Matchers.endsWith(BASE_URL)));
    }
    
    @Test
    @DisplayName("GET one starship works throught all layers And check response is complete")
    void getStarshipByIdWorksThroughAllLayersAndCheckForACompleteBody() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/{id}", "W5pvAw0r"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.name", is("StarshipToGet1")))
        .andExpect(jsonPath("$.engines", is("engine1")))
        .andExpect(jsonPath("$.description", is("description1")))
        .andExpect(jsonPath("$.height", is(1.0)))
        .andExpect(jsonPath("$.width", is(2.0)))
        .andExpect(jsonPath("$.lenght", is(3.0)))
        .andExpect(jsonPath("$.weight", is(4.5)))
        .andExpect(jsonPath("$.manufacturer").exists())
        .andExpect(jsonPath("$.manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.manufacturer.name", is("ManufacturerToGet1")))
        .andExpect(jsonPath("$.hyperdriveSystem.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.hyperdriveSystem.name", is("HyperdriveToGet1")))
        .andExpect(jsonPath("$.hyperdriveSystem.manufacturer").exists())
        .andExpect(jsonPath("$.hyperdriveSystem.manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.hyperdriveSystem.manufacturer.name", is("ManufacturerToGet1")))
        .andExpect(jsonPath("$.weapons").exists())
        .andExpect(jsonPath("$.weapons", hasSize(3)))
        .andExpect(jsonPath("$.weapons[0].id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.weapons[0].name", is("WeaponToGet1")))
        .andExpect(jsonPath("$.weapons[0].manufacturer").exists())
        .andExpect(jsonPath("$.weapons[0].manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.weapons[0].manufacturer.name", is("ManufacturerToGet1")))
        .andExpect(jsonPath("$.weapons[1].id", is("Kdp5qpxj")))
        .andExpect(jsonPath("$.weapons[1].name", is("WeaponToGet2")))
        .andExpect(jsonPath("$.weapons[1].manufacturer").exists())
        .andExpect(jsonPath("$.weapons[1].manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.weapons[1].manufacturer.name", is("ManufacturerToGet1")))
        .andExpect(jsonPath("$.weapons[2].id", is("mbLbXp35")))
        .andExpect(jsonPath("$.weapons[2].name", is("WeaponToGet3")))
        .andExpect(jsonPath("$.weapons[2].manufacturer").exists())
        .andExpect(jsonPath("$.weapons[2].manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.weapons[2].manufacturer.name", is("ManufacturerToGet1")))
        // HateOAS links tests
        .andExpect(jsonPath("$._links.self.href", is(Matchers.endsWith(BASE_URL + "/" + "W5pvAw0r"))))
        .andExpect(jsonPath("$._links.starships.href").value(Matchers.endsWith(BASE_URL)));
    }
    
    @Test
    @DisplayName("POST a starship json body works throught all layers")
    void createStarshipWorksThroughAllLayers() throws Exception {        
        
        String json = "{\r\n\"name\":\"StarshipCreated1\",\r\n\"engines\":\"engine\",\r\n\"height\": 1.0,\r\n\"width\": 2.0,\r\n\"lenght\": 3.0,\r\n\"weight\": 4.5,\r\n\"description\": \"description\",\r\n\"manufacturer\": {\r\n\"id\": \"W5pvAw0r\",\r\n\"name\": \"ManufacturerToGet1\"\r\n},\r\n\"hyperdriveSystem\": {\r\n\"id\":\"W5pvAw0r\",\r\n\"name\": \"HyperdriveToGet1\",\r\n\"manufacturer\": {\r\n\"id\": \"W5pvAw0r\",\r\n\"name\": \"ManufacturerToGet1\"\r\n}\r\n},\r\n\"weapons\": [\r\n{\r\n\"id\": \"W5pvAw0r\",\r\n\"name\": \"WeaponToGet1\",\r\n\"manufacturer\": {\r\n\"id\": \"W5pvAw0r\",\r\n\"name\": \"ManufacturerToGet1\"\r\n}\r\n},\r\n{\r\n\"id\": \"Kdp5qpxj\",\r\n\"name\": \"WeaponToGet2\",\r\n\"manufacturer\": {\r\n\"id\": \"W5pvAw0r\",\r\n\"name\": \"ManufacturerToGet1\"\r\n}\r\n}\r\n]\r\n}";
        
        mockMvc	.perform(post(BASE_URL)	.contentType(APPLICATION_JSON_UTF8)
        .content(json))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name", is("StarshipCreated1")))
        .andExpect(jsonPath("$.engines", is("engine")))
        .andExpect(jsonPath("$.description", is("description")))
        .andExpect(jsonPath("$.height", is(1.0)))
        .andExpect(jsonPath("$.width", is(2.0)))
        .andExpect(jsonPath("$.lenght", is(3.0)))
        .andExpect(jsonPath("$.weight", is(4.5)))
        .andExpect(jsonPath("$.manufacturer").exists())
        .andExpect(jsonPath("$.manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.manufacturer.name", is("ManufacturerToGet1")))
        .andExpect(jsonPath("$.hyperdriveSystem.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.hyperdriveSystem.name", is("HyperdriveToGet1")))
        .andExpect(jsonPath("$.hyperdriveSystem.manufacturer").exists())
        .andExpect(jsonPath("$.hyperdriveSystem.manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.hyperdriveSystem.manufacturer.name", is("ManufacturerToGet1")))
        .andExpect(jsonPath("$.weapons").exists())
        .andExpect(jsonPath("$.weapons", hasSize(2)))
        .andExpect(jsonPath("$.weapons[0].id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.weapons[0].name", is("WeaponToGet1")))
        .andExpect(jsonPath("$.weapons[0].manufacturer").exists())
        .andExpect(jsonPath("$.weapons[0].manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.weapons[0].manufacturer.name", is("ManufacturerToGet1")))
        .andExpect(jsonPath("$.weapons[1].id", is("Kdp5qpxj")))
        .andExpect(jsonPath("$.weapons[1].name", is("WeaponToGet2")))
        .andExpect(jsonPath("$.weapons[1].manufacturer").exists())
        .andExpect(jsonPath("$.weapons[1].manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.weapons[1].manufacturer.name", is("ManufacturerToGet1")))
        // HateOAS links tests
        .andExpect(jsonPath("$._links.self.href", is(Matchers.endsWith(BASE_URL + "/" + "01973938"))))
        .andExpect(jsonPath("$._links.starships.href").value(Matchers.endsWith(BASE_URL)));
    }
    
    @Test
    @DisplayName("POST a starship json body works throught all layers")
    void createMinimalStarshipWorksThroughAllLayers() throws Exception {        
        
        String json = "{\r\n\"name\":\"StarshipCreated2\"\r\n}";
        log.info("Body: %s", json);
        
        mockMvc	.perform(post(BASE_URL)	.contentType(APPLICATION_JSON_UTF8)
        .content(json))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name", is("StarshipCreated2")))
        .andExpect(jsonPath("$.engines", is(nullValue())))
        .andExpect(jsonPath("$.description", is(nullValue())))
        .andExpect(jsonPath("$.height", is(0.0)))
        .andExpect(jsonPath("$.width", is(0.0)))
        .andExpect(jsonPath("$.lenght", is(0.0)))
        .andExpect(jsonPath("$.weight", is(0.0)))
        .andExpect(jsonPath("$.manufacturer", is(nullValue())))
        .andExpect(jsonPath("$.hyperdriveSystem", is(nullValue())))
        .andExpect(jsonPath("$.weapons", is(nullValue())))
        // HateOAS links tests
        .andExpect(jsonPath("$._links.starships.href").value(Matchers.endsWith(BASE_URL)));
    }
    
    
    @Test
    @DisplayName("PUT a starship json body works throught all layers, it removes a weapon and update some other data")
    void updateStarshipWorksThroughAllLayers() throws Exception {        
        
        String json = "{\r\n\"name\":\"StarshipUpdated\",\r\n\"engines\":\"engineUpdated\",\r\n\"height\": 0.1,\r\n\"width\": 2.3,\r\n\"lenght\": 4.5,\r\n\"weight\": 6.7,\r\n\"description\": \"description\",\r\n\"manufacturer\": {\r\n\"id\": \"Kdp5qpxj\",\r\n\"name\": \"ManufacturerToGet2\"\r\n},\r\n\"hyperdriveSystem\": {\r\n\"id\":\"Kdp5qpxj\",\r\n\"name\": \"HyperdriveToGet1\",\r\n\"manufacturer\": {\r\n\"id\": \"Kdp5qpxj\",\r\n\"name\": \"ManufacturerToGet2\"\r\n}\r\n},\r\n\"weapons\": [\r\n{\r\n\"id\": \"W5pvAw0r\",\r\n\"name\": \"WeaponToGet1\",\r\n\"manufacturer\": {\r\n\"id\": \"W5pvAw0r\",\r\n\"name\": \"ManufacturerToGet1\"\r\n}\r\n}\r\n]\r\n}";
        
        mockMvc	.perform(put(BASE_URL + "/" + "mbLbXp35")	.contentType(APPLICATION_JSON_UTF8)
        .content(json))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name", is("StarshipUpdated")))
        .andExpect(jsonPath("$.engines", is("engineUpdated")))
        .andExpect(jsonPath("$.description", is("description")))
        .andExpect(jsonPath("$.height", is(0.1)))
        .andExpect(jsonPath("$.width", is(2.3)))
        .andExpect(jsonPath("$.lenght", is(4.5)))
        .andExpect(jsonPath("$.weight", is(6.7)))
        .andExpect(jsonPath("$.manufacturer").exists())
        .andExpect(jsonPath("$.manufacturer.id", is("Kdp5qpxj")))
        .andExpect(jsonPath("$.manufacturer.name", is("ManufacturerToGet2")))
        .andExpect(jsonPath("$.hyperdriveSystem.id", is("Kdp5qpxj")))
        .andExpect(jsonPath("$.hyperdriveSystem.name", is("HyperdriveToGet2")))
        .andExpect(jsonPath("$.hyperdriveSystem.manufacturer").exists())
        .andExpect(jsonPath("$.hyperdriveSystem.manufacturer.id", is("Kdp5qpxj")))
        .andExpect(jsonPath("$.hyperdriveSystem.manufacturer.name", is("ManufacturerToGet2")))
        .andExpect(jsonPath("$.weapons").exists())
        .andExpect(jsonPath("$.weapons", hasSize(1)))
        .andExpect(jsonPath("$.weapons[0].id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.weapons[0].name", is("WeaponToGet1")))
        .andExpect(jsonPath("$.weapons[0].manufacturer").exists())
        .andExpect(jsonPath("$.weapons[0].manufacturer.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.weapons[0].manufacturer.name", is("ManufacturerToGet1")))
        // HateOAS links tests
        .andExpect(jsonPath("$._links.self.href", is(Matchers.endsWith(BASE_URL + "/" + "mbLbXp35"))))
        .andExpect(jsonPath("$._links.starships.href").value(Matchers.endsWith(BASE_URL)));
    }
    
    @Test
    @DisplayName("DEL a starship works throught all layers")
    void deleteStarshipWorksThroughAllLayers() throws Exception {        
        mockMvc	.perform(delete(BASE_URL + "/{id}", "GELQkpdQ").contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.deleted").exists())
        .andExpect(jsonPath("$.deleted").value(true))
        .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("DEL a starship throws error")
    void deleteStarshipThrowsError() throws Exception {        
        mockMvc	.perform(delete(BASE_URL + "/{id}", "").contentType(APPLICATION_JSON_UTF8))
        .andExpect(status().is4xxClientError());
    }
}

