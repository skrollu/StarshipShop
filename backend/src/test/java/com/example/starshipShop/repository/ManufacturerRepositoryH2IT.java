package com.example.starshipshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.example.starshipshop.repository.jpa.Manufacturer;

/**
* @author Skrollu
*
*         Test some repository operations on an H2 in memory Database. Same
*         test class as {@link ManufacturerRepositoryMysqlIT}
*/
@Disabled("H2 database cannot work: user and more table names are not allowed")
@ActiveProfiles("h2-mysql")
@SpringBootTest
// @DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Simple Manufacturer repository with an H2 in memory database")
public class ManufacturerRepositoryH2IT {

	@Autowired
	private ManufacturerRepository manufacturerRepository;
	
	@Test
	@DisplayName("Find all manufacturers and check the size of the list")
	void shouldGetAllManufacturersFromDb() throws Exception {
		List<Manufacturer> manufacturers = manufacturerRepository.findAll();
		assertEquals(6, manufacturers.size());
	}
	
	@Test
	@Disabled
	@DisplayName("Get a manufacturer by id")
	void shouldGetManufacturerByIdFromDb() throws Exception {
		final String expectedName = "ManufacturerToGet1";
		Manufacturer manufacturer = manufacturerRepository.getReferenceById(1L);
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