package com.example.starShipShop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.example.starshipShop.controller.ManufacturerController;

//@WebMvcTest(controllers = ManufacturerController.class)

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ManufacturerControllerTest {

	@Autowired
	private ManufacturerController manufacturerController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(manufacturerController).isNotNull();
	}

	/*
	 * @Autowired private MockMvc mockMvc;
	 * 
	 * @Test public void testgetManufacturers() throws Exception { mockMvc
	 * .perform(get("/api/v1/manufacturers")) .andExpect(status().isOk()); }
	 */
}