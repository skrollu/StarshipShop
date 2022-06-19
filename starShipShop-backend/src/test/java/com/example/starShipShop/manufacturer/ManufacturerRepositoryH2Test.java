package com.example.starShipShop.manufacturer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.repository.ManufacturerRepository;

/**
 * @author Mathieu
 *
 *         Test some repository operations on an H2 in memory Database. Same
 *         test class as {@link ManufacturerRepositoryMysqlTest}
 */
@DataJpaTest
@ActiveProfiles("test-h2")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ManufacturerRepositoryH2Test {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ManufacturerRepository manufacturerRepository;

	@Test
	void shouldGetManufacturersFromDb() throws Exception {
		List<Manufacturer> manufacturers = manufacturerRepository.findAll();
		assertEquals(2, manufacturers.size());
	}

	@Test
	void shouldGetManufacturerByIdFromDb() throws Exception {
		final String expectedName = "Koensayr Manufacturing";
		Manufacturer manufacturer = manufacturerRepository.getById(1L);
		assertNotNull(manufacturer);
		assertEquals(manufacturer.getId(), 1L);
		assertEquals(manufacturer.getName(), expectedName);
	}

	@Test
	void shouldGetManufacturerByNameFromDb() throws Exception {
		List<Manufacturer> manufacturers = manufacturerRepository.findAll();
		final String expected = "Koensayr Manufacturing";
		Manufacturer manufacturer = manufacturers	.stream()
													.filter(m -> m	.getName()
																	.equals(expected))
													.findFirst()
													.get();
		assertNotNull(manufacturer);
		assertNotEquals(manufacturer.getName(), expected + "g");
		assertEquals(manufacturer.getName(), expected);
	}

	@Test
	void shouldSaveNewManufacturerAndGetItByNameFromDb() throws Exception {
		final String manufacturerName = "Corellian Engineering Corporation";
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setName(manufacturerName);
		this.entityManager.persist(manufacturer);

		Manufacturer result = this.manufacturerRepository	.findByName(manufacturerName)
															.get();
		assertNotNull(result);
		assertEquals(result.getId(), 3L);
		assertEquals(result.getName(), manufacturerName);
	}
}
