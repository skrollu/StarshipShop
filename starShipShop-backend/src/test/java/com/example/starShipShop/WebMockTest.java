package com.example.starShipShop;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.starshipShop.StarshipShopApplication;
import com.example.starshipShop.controller.ManufacturerController;
import com.example.starshipShop.repository.ManufacturerRepository;
import com.example.starshipShop.service.ManufacturerService;

@WebMvcTest(ManufacturerController.class)
public class WebMockTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ManufacturerService manufacturerService;

	@MockBean
	private ManufacturerRepository manufacturerRepository;

	/**
	 * Avoid NoSuchBeanDefinitionException, this bean is present in
	 * {@link StarshipShopApplication}
	 */
	@MockBean
	private CommandLineRunner initDatabase;

	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		when(manufacturerService.getManufacturers()).thenReturn(new ArrayList<>());
		this.mockMvc.perform(get("/api/v1/manufacturers"))
					.andDo(print())
					.andExpect(status().isOk());
		// .andExpect(content().string(containsString("Hello, Mock")));
	}
}