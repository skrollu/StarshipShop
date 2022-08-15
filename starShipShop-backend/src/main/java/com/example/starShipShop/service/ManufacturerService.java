package com.example.starshipShop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.starshipShop.dto.ManufacturerDto;
import com.example.starshipShop.dto.ManufacturerRequestInput;
import com.example.starshipShop.exception.ResourceNotFoundException;
import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.mapper.StarshipShopMapper;
import com.example.starshipShop.mapper.converter.IdToHashConverter;
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

	public List<Manufacturer> getManufacturers() {
		return manufacturerRepository.findAll();
	}

	public List<ManufacturerDto> getManufacturersDto() {
		return this	.getManufacturers()
					.stream()
					.map(m -> mapper.toManufacturerDto(m))
					.collect(Collectors.toList());
	}

	public Optional<Manufacturer> getManufacturerById(final Long id) {
		return manufacturerRepository.findById(id);

	}

	public Optional<ManufacturerDto> getManufacturerDtoById(final Long id) {
		return this	.getManufacturerById(id)
					.map(m -> mapper.toManufacturerDto(m));
	}

	public ManufacturerDto createManufacturer(final ManufacturerRequestInput mri) {
		this.checkManufacturerRequestInput(mri);
		return mapper.toManufacturerDto(manufacturerRepository.save(mapper.fromManufaturerRequestInput(mri)));
	}

	public ManufacturerDto updateManufacturer(final Long id, ManufacturerRequestInput mri) {
		Assert.notNull(id, String.format("id cannot be null."));
		this.checkManufacturerRequestInput(mri);
		this.checkManufacturerExist(id);
		Manufacturer manufacturer = mapper.fromManufaturerRequestInput(mri);
		manufacturer.setId(id);
		return mapper.toManufacturerDto(manufacturerRepository.save(manufacturer));
	}

	public void deleteManufacturer(final Long id) {
		Assert.notNull(id, String.format("id cannot be null."));
		Manufacturer manufacturerToDelete = this.checkManufacturerExist(id);
		manufacturerRepository.delete(manufacturerToDelete);
	}

	public void checkManufacturerRequestInput(ManufacturerRequestInput mri) {
		Assert.notNull(mri, String.format("Manufacturer cannot be null."));
		Assert.notNull(mri.getName(), String.format("Name of Manufacturer cannot be null."));
		Assert.hasText(mri.getName(), String.format("Name of Manufacturer cannot be empty."));
	}
	
	public void checkManufacturerDto(ManufacturerDto dto) {
		Assert.notNull(dto, String.format("Manufacturer cannot be null."));
		Assert.notNull(dto.getId(), String.format("Id of Manufacturer cannot be null."));
		Assert.notNull(dto.getName(), String.format("Name of Manufacturer cannot be null."));
		Assert.hasText(dto.getName(), String.format("Name of Manufacturer cannot be empty."));
	}

	public Manufacturer checkManufacturerExist(Long id) {
		return manufacturerRepository	.findById(id)
								.orElseThrow(
										() -> new ResourceNotFoundException("Manufacturer doesn't exist with this id: "
												+ IdToHashConverter.convertToHash(id)));
	}
}