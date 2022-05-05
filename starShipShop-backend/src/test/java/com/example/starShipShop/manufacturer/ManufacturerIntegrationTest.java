package com.example.starShipShop.manufacturer;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.starshipShop.repository.ManufacturerRepository;

@ActiveProfiles("test-h2")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ManufacturerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ManufacturerRepository manufacturerRepository;

	@Test
	void getManufacturersWorksThroughAllLayers() throws Exception {
		this.mockMvc.perform(get("/api/v1/manufacturers"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("TestManufacturer")));
	}

	@Test
	void getManufacturerByIdWorksThroughAllLayers() throws Exception {
		this.mockMvc.perform(get("/api/v1/manufacturers/{id}", 2))
					.andDo(print())
					.andExpect(jsonPath("$.name").exists())
					.andExpect(jsonPath("$.name", is("TestManufacturer")))
					.andExpect(jsonPath("$.id").exists())
					.andExpect(jsonPath("$.id", not(1)))
					.andExpect(jsonPath("$.id", is(2)))
					.andExpect(status().isOk());
	}

	@Test
	void getManufacturerByNameWorksThroughAllLayers() throws Exception {
		final String name = "Koensayr Manufacturing";
		this.mockMvc.perform(get("/api/v1/manufacturers/name/{name}", name))
					.andDo(print())
					.andExpect(jsonPath("$.name").exists())
					.andExpect(jsonPath("$.name", is(name)))
					.andExpect(jsonPath("$.id").exists())
					.andExpect(jsonPath("$.id", not(2)))
					.andExpect(jsonPath("$.id", is(1)))
					.andExpect(status().isOk());
	}

	/* 
	@Test
	void getManufacturerByNameAndThrowNoSuchElementException() throws Exception {
		final String name = "bad argument";
		this.mockMvc.perform(get("/api/v1/manufacturers/name/{name}", name))
					.contentType(MediaType.APPLICATION_JSON));
					//.andExpect(status().isInternalServerError());
					//.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException));
		// .andExpect(result -> assertEquals("resource not found", result
		// .getResolvedException()
		// .getMessage()));

	} */
}
