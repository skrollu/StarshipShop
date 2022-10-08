package com.example.starshipShop.manufacturer;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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
import com.example.starshipShop.dto.ManufacturerRequestInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@ActiveProfiles("test-h2")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManufacturerIntegrationTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
	MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	public static final String BASE_URL = "/api/v1/manufacturers";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@Order(1)
	@DisplayName("GET all manufacturers works throught all layers")
	void getManufacturersWorksThroughAllLayers() throws Exception {
		this.mockMvc.perform(get(BASE_URL))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("ManufacturerToGet1")))
		.andExpect(jsonPath("$._embedded.manufacturers[0]").exists())
		.andExpect(jsonPath("$._embedded.manufacturers[0]._links.self.href").exists())
		.andExpect(jsonPath("$._embedded.manufacturers[0]._links.self.href",
		is(Matchers.containsString(BASE_URL + "/" + "W5pvAw0r"))))
		.andExpect(jsonPath("$._links.manufacturers.href").value(Matchers.containsString(BASE_URL)));
	}
	@Test
	@DisplayName("GET one manufacturer works throught all layers")
	void getManufacturerByIdWorksThroughAllLayers() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "/{id}", "W5pvAw0r"))
		.andDo(print())
		.andExpect(jsonPath("$.name").exists())
		.andExpect(jsonPath("$.name", is("ManufacturerToGet1")))
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.id", not("test")))
		.andExpect(jsonPath("$.id", is("W5pvAw0r")))
		.andExpect(jsonPath("$._links.self.href", is(Matchers.containsString(BASE_URL + "/" + "W5pvAw0r"))))
		.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("POST manufacturer works throught all layers")
	public void createManufacturerWorksThroughAllLayers() throws Exception {
		ManufacturerRequestInput requestObj = new ManufacturerRequestInput("ManufacturerCreated");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
		.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);
		
		mockMvc	.perform(post(BASE_URL)	.contentType(APPLICATION_JSON_UTF8)
		.content(requestJson))
		.andExpect(jsonPath("$.name").value("ManufacturerCreated"))
		.andExpect(jsonPath("$.id").exists())
		.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("POST manufacturer with empty name throw Illegal Argument Exception")
	public void createManufacturerWithEmptyNameThrowIllegalArgumentException() throws Exception {
		ManufacturerRequestInput requestObj = new ManufacturerRequestInput("");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
		.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);
		
		mockMvc	.perform(post(BASE_URL)	.contentType(APPLICATION_JSON_UTF8)
		.content(requestJson))
		.andExpect(content().string(containsString("Name of Manufacturer cannot be empty.")))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("POST manufacturer with null name throw Illegal Argument Exception")
	public void createManufacturerWithNullNameThrowIllegalArgumentException() throws Exception {
		ManufacturerRequestInput requestObj = new ManufacturerRequestInput();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
		.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);
		
		mockMvc	.perform(post(BASE_URL)	.contentType(APPLICATION_JSON_UTF8)
		.content(requestJson))
		.andExpect(content().string(containsString("Name of Manufacturer cannot be null.")))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("PUT manufacturer works throught all layers")
	public void updateManufacturerWorksThroughAllLayers() throws Exception {
		final String name = "ManufacturerToUpdate3";
		
		ManufacturerRequestInput requestObj = new ManufacturerRequestInput();
		requestObj.setName(name);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
		.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);
		
		mockMvc	.perform(put(BASE_URL + "/mbLbXp35").contentType(APPLICATION_JSON_UTF8)
		.content(requestJson))
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.id").value("mbLbXp35"))
		.andExpect(jsonPath("$.name").value(name))
		.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("PUT manufacturer with empty name throw Illegal Argument Exception")
	public void updateManufacturerWithEmptyNameThrowIllegalArgumentException() throws Exception {
		final String name = "";
		
		ManufacturerRequestInput requestObj = new ManufacturerRequestInput();
		requestObj.setName(name);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
		.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);
		
		mockMvc	.perform(put(BASE_URL + "/mbLbXp35").contentType(APPLICATION_JSON_UTF8)
		.content(requestJson))
		.andExpect(content().string(containsString("Name of Manufacturer cannot be empty.")))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("PUT manufacturer with null name throw Illegal Argument Exception")
	public void updateManufacturerWithNullNameThrowIllegalArgumentException() throws Exception {
		ManufacturerRequestInput requestObj = new ManufacturerRequestInput();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
		.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);
		
		mockMvc	.perform(put(BASE_URL + "/mbLbXp35").contentType(APPLICATION_JSON_UTF8)
		.content(requestJson))
		.andExpect(content().string(containsString("Name of Manufacturer cannot be null.")))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("PUT manufacturer with wrong id throw Resource not found exception by the HashToIdConverter")
	public void updateManufacturerThrowResourceNotFoundException() throws Exception {
		final String name = "ManufacturerToUpdate3";
		
		ManufacturerRequestInput requestObj = new ManufacturerRequestInput();
		requestObj.setName(name);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
		.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);
		
		mockMvc	.perform(put(BASE_URL + "/wrongId").contentType(APPLICATION_JSON_UTF8)
		.content(requestJson))
		.andExpect(content().string(Matchers.startsWith("ADVICE")))
		.andExpect(content().string(containsString("Cannot convert hash to id with the given hash:")))
		.andExpect(content().string(Matchers.endsWith("wrongId")))
		.andExpect(status().isNotFound());
	}
	
	
	@Test
	@DisplayName("DELETE manufacturer works throught all layers")
	public void deleteManufacturerWorksThroughAllLayers() throws Exception {
		// Id of a not joined Manufacturer to avoid nested errors or errors caused by delete before other tests ended.
		mockMvc	.perform(delete(BASE_URL + "/{id}", "qZpyYpYM").contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.deleted").exists())
		.andExpect(jsonPath("$.deleted").value(true))
		.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("DELETE manufacturer with wrong id throw Resource not found exception by the HashToIdConverter")
	public void deleteManufacturerThrowResourceNotFoundException() throws Exception {
		mockMvc	.perform(delete(BASE_URL + "/{id}", "wrongId").contentType(APPLICATION_JSON_UTF8))
		.andExpect(content().string(Matchers.startsWith("ADVICE")))
		.andExpect(content().string(containsString("Cannot convert hash to id with the given hash:")))
		.andExpect(content().string(Matchers.endsWith("wrongId")))
		.andExpect(status().isNotFound());
	}
}
