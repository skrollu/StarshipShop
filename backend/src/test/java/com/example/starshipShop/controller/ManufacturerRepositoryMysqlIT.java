package com.example.starshipShop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import com.example.starshipShop.repository.ManufacturerRepository;
import com.example.starshipShop.repository.jpa.Manufacturer;

/**
 *  @author Skrollu 
 * 
 * Test some repository operations on a MySQL Database.
 * Same test class as {@link ManufacturerRepositoryH2IT} 
 */
@DataJpaTest
@ActiveProfiles("test-mysql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Simple Manufacturer repository with a MySQL database")
public class ManufacturerRepositoryMysqlIT {

	@Autowired
	private ManufacturerRepository manufacturerRepository;
		
	@Test
	@DisplayName("Find all manufacturers and check the size of the list")
	void shouldGetAllManufacturersFromDb() throws Exception {
		List<Manufacturer> manufacturers = manufacturerRepository.findAll();
		assertEquals(6, manufacturers.size());
	}
	
	@Test
	@DisplayName("Get a manufacturer by id")
	void shouldGetManufacturerByIdFromDb() throws Exception {
		final String expectedName = "ManufacturerToGet1";
		Manufacturer manufacturer = manufacturerRepository.getById(1L);
		assertNotNull(manufacturer);
		assertEquals(manufacturer.getId(), 1L);
		assertEquals(manufacturer.getName(), expectedName);
	}
	
	@Test
	@DisplayName("Find all manufacturers and retrieve one by name in the obtained list")
	void shouldGetManufacturerByNameFromDb() throws Exception {
		List<Manufacturer> manufacturers = manufacturerRepository.findAll();
		final String expected = "ManufacturerToGet1";
		Manufacturer manufacturer = manufacturers	.stream()
		.filter(m -> m	.getName()
		.equals(expected))
		.findFirst()
		.get();
		assertNotNull(manufacturer);
		assertNotEquals(manufacturer.getName(), expected + "wrong");
		assertEquals(manufacturer.getName(), expected);
	}
}
