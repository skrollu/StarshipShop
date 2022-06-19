package com.example.starshipShop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.starshipShop.dto.ManufacturerDto;
import com.example.starshipShop.exception.ResourceNotFoundException;
import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.mapper.StarshipShopMapper;
import com.example.starshipShop.repository.ManufacturerRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Service
public class ManufacturerService {

	@Autowired
	private final ManufacturerRepository manufacturerRepository;

	@Autowired
	private final StarshipShopMapper mapper;

	public List<ManufacturerDto> getManufacturers() {
		return manufacturerRepository	.findAll()
										.stream()
										.map(m -> mapper.toManufacturerDto(m))
										.collect(Collectors.toList());
	}

	public Optional<ManufacturerDto> getManufacturerById(final Long id) {
		return manufacturerRepository	.findById(id)
										.map(m -> mapper.toManufacturerDto(m));
	}

	public ManufacturerDto createManufacturer(final ManufacturerDto dto) {
		Assert.notNull(dto.getName(), String.format("Name cannot be null."));
		Assert.hasText(dto.getName(), String.format("Name cannot be empty."));
		dto.setId(null);
		return mapper.toManufacturerDto(manufacturerRepository.save(mapper.toManufacturer(dto)));
	}

	public ManufacturerDto updateManufacturer(final Long id, ManufacturerDto dto) {
		Assert.notNull(dto.getName(), String.format("Name cannot be null."));
		Assert.hasText(dto.getName(), String.format("Name cannot be empty."));
		manufacturerRepository	.findById(id)
								.orElseThrow(
										() -> new ResourceNotFoundException("Manufacturer doesn't exist with this id"));
		Manufacturer manufacturer = mapper.toManufacturer(dto);
		manufacturer.setId(id);
		return mapper.toManufacturerDto(manufacturerRepository.save(manufacturer));
	}

	public void deleteManufacturer(final Long id) {
		Manufacturer manufacturerToDelete = manufacturerRepository	.findById(id)
																	.orElseThrow(() -> new ResourceNotFoundException(
																			"Manufacturer doesn't exist with this id"));
		manufacturerRepository.delete(manufacturerToDelete);
	}

}