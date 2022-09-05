package com.example.starshipShop.hyperdriveSystem;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class HyperdriveSystemIntegrationTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
	MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	public static final String BASE_URL = "/api/v1/hyperdriveSystems";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("GET all hyperdriveSystems works throught all layers")
	void getManufacturersWorksThroughAllLayers() throws Exception {
		this.mockMvc.perform(get(BASE_URL))
		.andDo(log())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Hyperdrive1")))
		.andExpect(content().string(containsString("Hyperdrive2")))
		.andExpect(jsonPath("$._embedded.hyperdriveSystems[0]").exists())
		.andExpect(jsonPath("$._embedded.hyperdriveSystems[0]._links.self.href").exists())
		.andExpect(jsonPath("$._links.self.href").value(Matchers.endsWith(BASE_URL)));
	}
}
