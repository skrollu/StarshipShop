package com.example.starshipShop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.starshipShop.dto.HyperdriveSystemDto;
import com.example.starshipShop.dto.HyperdriveSystemRequestInput;
import com.example.starshipShop.dto.ManufacturerDto;
import com.example.starshipShop.exception.ResourceNotFoundException;
import com.example.starshipShop.jpa.HyperdriveSystem;
import com.example.starshipShop.mapper.StarshipShopMapper;
import com.example.starshipShop.mapper.converter.HashToIdConverter;
import com.example.starshipShop.mapper.converter.IdToHashConverter;
import com.example.starshipShop.repository.HyperdriveSystemRepository;

import lombok.Data;

@Data
@Service
public class HyperdriveSystemService {

	@Autowired
	private ManufacturerService manufacturerService;

	@Autowired
	private HyperdriveSystemRepository hyperdriveSystemRepository;

	@Autowired
	private final StarshipShopMapper mapper;

	public List<HyperdriveSystem> getHyperdriveSystems() {
		return hyperdriveSystemRepository.findAll();
	}

	public List<HyperdriveSystemDto> getHyperdriveSystemsDto() {
		return this	.getHyperdriveSystems()
					.stream()
					.map(h -> mapper.toHyperdriveSystemDto(h))
					.collect(Collectors.toList());
	}

	public Optional<HyperdriveSystem> getHyperdriveSystemById(final Long id) {
		return hyperdriveSystemRepository.findById(id);
	}

	public Optional<HyperdriveSystemDto> getHyperdriveSystemDtoById(final Long id) {
		return this	.getHyperdriveSystemById(id)
					.map(h -> mapper.toHyperdriveSystemDto(h));
	}

	public HyperdriveSystemDto createHyperdriveSystem(HyperdriveSystemRequestInput hsri) {
		Assert.notNull(hsri.getName(), String.format("Hyperdrive System name cannot be null."));
		Assert.hasText(hsri.getName(), String.format("Hyperdrive System name cannot be empty."));
		Assert.notNull(hsri.getManufacturer(), String.format("Manufacturer cannot be null."));
		Assert.notNull(hsri	.getManufacturer()
							.getId(),
				String.format("Manufacturer's id cannot be null."));
		Assert.hasText(hsri	.getManufacturer()
							.getName(),
				String.format("Manufacturer's name cannot be empty."));
		
		this.manufacturerService	.getManufacturerById(hsri.getManufacturer()
																								.getId())
																	.orElseThrow(() -> new ResourceNotFoundException(
																			"The given Manufacturer doesn't exist with this id: " + IdToHashConverter.convertToHash(hsri.getManufacturer().getId())));
		
		return mapper.toHyperdriveSystemDto(hyperdriveSystemRepository.save(mapper.fromHyperdriveSystemRequestInput(hsri)));
	}

	public HyperdriveSystemDto updateHyperdriveSystem(final Long id, HyperdriveSystemRequestInput hsri) {
		Assert.notNull(hsri.getName(), String.format("Hyperdrive System name cannot be null."));
		Assert.hasText(hsri.getName(), String.format("Hyperdrive System name cannot be empty."));
		Assert.notNull(hsri.getManufacturer(), String.format("Manufacturer cannot be null."));
		Assert.notNull(hsri	.getManufacturer()
							.getId(),
				String.format("Manufacturer's id cannot be null."));
		Assert.hasText(hsri	.getManufacturer()
							.getName(),
				String.format("Manufacturer's name cannot be empty."));
		this.manufacturerService.getManufacturerById(hsri	.getManufacturer()
															.getId())
								.orElseThrow(() -> new ResourceNotFoundException(
																			"The given Manufacturer doesn't exist with this id: " + IdToHashConverter.convertToHash(hsri.getManufacturer().getId())));
		HyperdriveSystem hs = mapper.fromHyperdriveSystemRequestInput(hsri);
		hs.setId(id);
		return mapper.toHyperdriveSystemDto(hyperdriveSystemRepository.save(hs));
	}

	public void deleteHyperdriveSystem(final Long id) {
				Assert.notNull(id, String.format("id cannot be null."));

		HyperdriveSystem hyperdriveSystemToDelete = hyperdriveSystemRepository	.findById(id)
																				.orElseThrow(
																						() -> new ResourceNotFoundException(
																								"HyperdriveSystem doesn't exist with this id: " 				+ IdToHashConverter.convertToHash(
																							id)));
		hyperdriveSystemRepository.delete(hyperdriveSystemToDelete);
	}
}
