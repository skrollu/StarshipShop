package com.starshipshop.starshipservice.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.isNull;
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

@ActiveProfiles("test-mysql")
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StarshipControllerIT {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private static final String BASE_URL = "/api/v1/starships";
    private static final String USER_USERNAME = "user@mail.com";
    private static final String USER_PASSWORD = "password";
    private static final String ADMIN_USERNAME = "admin@mail.com";
    private static final String ADMIN_PASSWORD = "password";

    @Autowired
    private MockMvc mockMvc;

    // GET /starships
    @Test
    @Order(1)
    @Tag("GET /starships")
    @DisplayName("GET all starships when not authenticated should works")
    void getStarships_whenNotAuthenticated_shouldWorks() throws Exception {
        this.mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.starships", hasSize(4)))
                .andExpect(jsonPath("$._embedded.starships[0].name", is("StarshipToGet1")))
                .andExpect(jsonPath("$._embedded.starships[1].name", is("StarshipToGet2")))
                .andExpect(jsonPath("$._embedded.starships[2].name", is("StarshipToUpdate1")))
                .andExpect(jsonPath("$._embedded.starships[3].name", is("StarshipToDelete1")))
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
    @Order(1)
    @Tag("GET /starships")
    @DisplayName("GET all starships when authenticated as User should works")
    void getStarships_whenAuthenticatedAsUser_shouldWorks() throws Exception {
        this.mockMvc.perform(get(BASE_URL).with(httpBasic(USER_USERNAME, USER_PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.starships", hasSize(4)))
                .andExpect(jsonPath("$._embedded.starships[0].name", is("StarshipToGet1")))
                .andExpect(jsonPath("$._embedded.starships[1].name", is("StarshipToGet2")))
                .andExpect(jsonPath("$._embedded.starships[2].name", is("StarshipToUpdate1")))
                .andExpect(jsonPath("$._embedded.starships[3].name", is("StarshipToDelete1")))
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

    // GET /starships/{id}
    @Test
    @Order(1)
    @Tag("GET /starships/{id}")
    @DisplayName("GET one starship when not authenticated should works and check all members")
    void getStarshipById_whenNotAuthenticated_shouldWorks() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/{id}", "W5pvAw0r"))
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
    @Order(1)
    @Tag("GET /starships/{id}")
    @DisplayName("GET one starship when authenticated as User should works and check all members")
    void getStarshipById_whenAuthenticatedAsUser_shouldWorks() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/{id}", "W5pvAw0r").with(httpBasic(USER_USERNAME, USER_PASSWORD)))
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

    // POST /starships
    @Test
    @Tag("POST /starships")
    @DisplayName("POST a starship json body when authenticated as Admin should works throught all layers")
    void createStarshipWorks_whenAuthenticatedAsAdmin_shouldThroughAllLayers() throws Exception {

        String json = "{\r\n    \"name\": \"StarshipCreated1\",\r\n    \"manufacturerId\": \"W5pvAw0r\",\r\n    \"hyperdriveSystemId\": \"W5pvAw0r\",\r\n    \"weapons\": [ { \"weaponId\": \"W5pvAw0r\" },{ \"weaponId\": \"W5pvAw0r\" }]\r\n}";

        mockMvc.perform(
                post(BASE_URL).with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)).contentType(APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("StarshipCreated1")))
                .andExpect(jsonPath("$.engines", is(nullValue())))
                .andExpect(jsonPath("$.description", is(nullValue())))
                .andExpect(jsonPath("$.height", is(0.0)))
                .andExpect(jsonPath("$.width", is(0.0)))
                .andExpect(jsonPath("$.lenght", is(0.0)))
                .andExpect(jsonPath("$.weight", is(0.0)))
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
                .andExpect(jsonPath("$.weapons[1].id", is("W5pvAw0r")))
                .andExpect(jsonPath("$.weapons[1].name", is("WeaponToGet1")))
                .andExpect(jsonPath("$.weapons[1].manufacturer").exists())
                .andExpect(jsonPath("$.weapons[1].manufacturer.id", is("W5pvAw0r")))
                .andExpect(jsonPath("$.weapons[1].manufacturer.name", is("ManufacturerToGet1")))
                // HateOAS links tests
                .andExpect(jsonPath("$._links.starships.href").value(Matchers.endsWith(BASE_URL)));
    }

    @Test
    @Tag("POST /starships")
    @DisplayName("POST a starship json body when authenticated as User should response 403")
    void createMinimalStarship_whenAuthenticatedAsUser_shouldResponse403() throws Exception {

        String json = "{\r\n\"name\":\"StarshipCreated2\"\r\n}";
        log.info("Body: %s", json);

        mockMvc.perform(post(BASE_URL).with(httpBasic(USER_USERNAME, USER_PASSWORD))
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isForbidden());
    }

    // PUT /starships/{id}
    @Test
    @Tag("PUT /starships/{id}")
    @DisplayName("PUT a starship json body when authenticated as Admin should works, it removes a weapon and update some other data")
    void updateStarship_whenAuthenticatedAsAdmin_shouldWorks() throws Exception {

        String json = "{\r\n    \"name\": \"StarshipUpdated\",\r\n    \"engines\": \"engineUpdated\",\r\n    \"description\": \"description\",\r\n    \"height\": 0.1,\r\n    \"width\": 2.3,\r\n    \"lenght\": 4.5,\r\n    \"weight\": 6.7,\r\n    \"manufacturerId\": \"Kdp5qpxj\",\r\n    \"hyperdriveSystemId\": \"Kdp5qpxj\",\r\n    \"weapons\": [ { \"weaponId\": \"W5pvAw0r\" } ]\r\n}";
        mockMvc.perform(put(BASE_URL + "/" + "mbLbXp35").with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))

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
    @Tag("PUT /starships/{id}")
    @DisplayName("PUT a starship json body when authenticated as User should response 403")
    void updateStarship_whenAuthenticatedAsUser_shouldWorks() throws Exception {

        String json = "{\r\n\"name\":\"StarshipUpdated\",\r\n\"engines\":\"engineUpdated\",\r\n\"height\": 0.1,\r\n\"width\": 2.3,\r\n\"lenght\": 4.5,\r\n\"weight\": 6.7,\r\n\"description\": \"description\",\r\n\"manufacturer\": {\r\n\"id\": \"Kdp5qpxj\",\r\n\"name\": \"ManufacturerToGet2\"\r\n},\r\n\"hyperdriveSystem\": {\r\n\"id\":\"Kdp5qpxj\",\r\n\"name\": \"HyperdriveToGet1\",\r\n\"manufacturer\": {\r\n\"id\": \"Kdp5qpxj\",\r\n\"name\": \"ManufacturerToGet2\"\r\n}\r\n},\r\n\"weapons\": [\r\n{\r\n\"id\": \"W5pvAw0r\",\r\n\"name\": \"WeaponToGet1\",\r\n\"manufacturer\": {\r\n\"id\": \"W5pvAw0r\",\r\n\"name\": \"ManufacturerToGet1\"\r\n}\r\n}\r\n]\r\n}";

        mockMvc.perform(put(BASE_URL + "/" + "mbLbXp35").with(httpBasic(USER_USERNAME, USER_PASSWORD))
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isForbidden());
    }

    // DELETE /starships/{id}
    @Test
    @Tag("DELETE /starships/{id}")
    @DisplayName("DELETE a starship when authenticated as Admin should works throught all layers")
    void deleteStarship_whenAuthenticatedAsAdmin_shouldWorksThroughAllLayers() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{id}", "GELQkpdQ").with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.deleted").value(true))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("DELETE /starships/{id}")
    @DisplayName("DELETE a starship when authenticated as Admin should throws error")
    void deleteStarship_whenAuthenticatedAsAdmin_shouldThrowsError() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{id}", "").with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError());
    }
}
