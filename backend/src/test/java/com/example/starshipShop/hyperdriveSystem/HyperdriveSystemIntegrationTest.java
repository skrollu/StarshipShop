package com.example.starshipShop.hyperdriveSystem;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@ActiveProfiles("test-h2")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HyperdriveSystemIntegrationTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
	MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	public static final String BASE_URL = "/api/v1/hyperdriveSystems";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@Order(1)
	@DisplayName("GET all hyperdriveSystems works throught all layers")
	void getHyperdriveSystemsWorksThroughAllLayers() throws Exception {
		this.mockMvc.perform(get(BASE_URL))
		.andDo(log())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$._embedded.hyperdriveSystems", hasSize(4)))
		.andExpect(content().string(containsString("HyperdriveToGet1")))
		.andExpect(content().string(containsString("HyperdriveToGet2")))
		.andExpect(jsonPath("$._embedded.hyperdriveSystems[0]").exists())
		.andExpect(jsonPath("$._embedded.hyperdriveSystems[0]._links.self.href").exists())
		.andExpect(jsonPath("$._links.self.href").value(Matchers.endsWith(BASE_URL)));
	}

	@Test
    @DisplayName("GET one hyperdriveSystem works throught all layers")
    void getOneHyperdriveSystemWorksThroughAllLayers() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/{id}", "W5pvAw0r"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is("W5pvAw0r")))
        .andExpect(jsonPath("$.name", is(Matchers.is("HyperdriveToGet1"))))
        // HateOAS links tests
        .andExpect(jsonPath("$._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "W5pvAw0r"))));
    }

	@Test
    @DisplayName("POST a hyperdriveSystem json body works throught all layers")
    void createMinimalHyperdriveSystemWorksThroughAllLayers() throws Exception {        
        
        String json = "{\r\n\"name\":\"HyperdriveSystemCreated\"\r\n}";
        
        mockMvc	.perform(post(BASE_URL)	.contentType(APPLICATION_JSON_UTF8)
        .content(json))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name", is("HyperdriveSystemCreated")))
        .andExpect(jsonPath("$.manufacturer", is(nullValue())))
        // HateOAS links tests
        .andExpect(jsonPath("$._links.hyperdriveSystems.href").value(Matchers.endsWith(BASE_URL)));
    }

    @Test
    @DisplayName("PUT a hyperdriveSystem json body works throught all layers, it removes the manufacturer and update some other data")
    void updateHyperdriveSystemWorksThroughAllLayers() throws Exception {        
        
        String json = "{\r\n\"name\": \"HyperdriveToUpdated3\"\r\n}";
        
        mockMvc	.perform(put(BASE_URL + "/" + "mbLbXp35")	.contentType(APPLICATION_JSON_UTF8)
        .content(json))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name", is("HyperdriveToUpdated3")))
        .andExpect(jsonPath("$.manufacturer", is(nullValue())))
        // HateOAS links tests
        .andExpect(jsonPath("$._links.self.href",
        is(Matchers.endsWith(BASE_URL + "/" + "mbLbXp35"))));
    }

	@Test
    @DisplayName("DEL a hyperdriveSystem works throught all layers")
    void deleteHyperdriveSystemWorksThroughAllLayers() throws Exception {        
        mockMvc	.perform(delete(BASE_URL + "/{id}", "mbLbXp35").contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.deleted").exists())
        .andExpect(jsonPath("$.deleted").value(true))
        .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("DEL a hyperdriveSystem with no id throws client error because this endpoint doesn't exist")
    void deleteHyperdriveSystemWThrowsClientError() throws Exception {        
        mockMvc	.perform(delete(BASE_URL + "/{id}", "").contentType(APPLICATION_JSON_UTF8))
        .andExpect(status().is4xxClientError());
    }
}
