package com.example.starshipshop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.example.starshipshop.common.exception.ResourceNotFoundException;
import com.example.starshipshop.domain.HyperdriveSystemDto;
import com.example.starshipshop.domain.HyperdriveSystemRequestInput;
import com.example.starshipshop.repository.HyperdriveSystemRepository;
import com.example.starshipshop.repository.jpa.HyperdriveSystem;
import com.example.starshipshop.service.mapper.StarshipShopMapper;
import com.example.starshipshop.service.mapper.converter.IdToHashConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HyperdriveSystemService {
	
	private final ManufacturerService manufacturerService;
	
	private final HyperdriveSystemRepository hyperdriveSystemRepository;
	
	private final StarshipShopMapper mapper;

	private final IdToHashConverter idToHashConverter;
	
	public List<HyperdriveSystem> getHyperdriveSystems() {
		return hyperdriveSystemRepository.findAll();
	}
	
	public List<HyperdriveSystemDto> getHyperdriveSystemsDto() {
		return this	.getHyperdriveSystems()
		.stream()
		.map(mapper::toHyperdriveSystemDto)
		.collect(Collectors.toList());
	}
	
	public Optional<HyperdriveSystem> getHyperdriveSystemById(final Long id) {
		return hyperdriveSystemRepository.findById(id);
	}
	
	public Optional<HyperdriveSystemDto> getHyperdriveSystemDtoById(final Long id) {
		return this	.getHyperdriveSystemById(id)
		.map(mapper::toHyperdriveSystemDto);
	}
	
	public HyperdriveSystemDto createHyperdriveSystem(final HyperdriveSystemRequestInput hsri) {
		this.checkHyperdriveSystemRequestInput(hsri);
		// Check Manufacturer
		if(hsri.getManufacturer() != null) {
			this.manufacturerService.checkManufacturerDto(hsri.getManufacturer());
			this.manufacturerService.checkManufacturerExist(hsri.getManufacturer().getId());
		}
		return mapper.toHyperdriveSystemDto(hyperdriveSystemRepository.save(mapper.fromHyperdriveSystemRequestInput(hsri)));
	}
	
	public HyperdriveSystemDto updateHyperdriveSystem(final Long id, final HyperdriveSystemRequestInput hsri) {
		Assert.notNull(id, String.format("id cannot be null."));
		this.checkHyperdriveSystemRequestInput(hsri);
		this.checkHyperdriveSystemExist(id);
		// Check Manufacturer
		if(hsri.getManufacturer() != null) {
			this.manufacturerService.checkManufacturerDto(hsri.getManufacturer());
			this.manufacturerService.checkManufacturerExist(hsri.getManufacturer().getId());
		}
		HyperdriveSystem hs = mapper.fromHyperdriveSystemRequestInput(hsri);
		hs.setId(id);
		return mapper.toHyperdriveSystemDto(hyperdriveSystemRepository.save(hs));
	}
	
	public void deleteHyperdriveSystem(final Long id) {
		Assert.notNull(id, String.format("id cannot be null."));
		HyperdriveSystem hyperdriveSystemToDelete = this.checkHyperdriveSystemExist(id);
		hyperdriveSystemRepository.delete(hyperdriveSystemToDelete);
	}
	
	public void checkHyperdriveSystemRequestInput(HyperdriveSystemRequestInput hsri) {
		Assert.notNull(hsri, String.format("HyperdriveSystem cannot be null."));
		Assert.notNull(hsri.getName(), String.format("Name of HyperdriveSystem cannot be null."));
		Assert.hasText(hsri.getName(), String.format("Name of HyperdriveSystem cannot be empty."));
	}
	
	public void checkHyperdriveSystemDto(HyperdriveSystemDto dto) {
		Assert.notNull(dto, String.format("HyperdriveSystem cannot be null."));
		Assert.notNull(dto.getId(), String.format("Id of HyperdriveSystem cannot be null."));
		Assert.notNull(dto.getName(), String.format("Name of HyperdriveSystem cannot be null."));
		Assert.hasText(dto.getName(), String.format("Name of HyperdriveSystem cannot be empty."));
	}
	
	public HyperdriveSystem checkHyperdriveSystemExist(Long id) {
		return hyperdriveSystemRepository	.findById(id)
		.orElseThrow(
		() -> new ResourceNotFoundException("HyperdriveSystem doesn't exist with this id: "
		+ idToHashConverter.convert(id)));
	}
}
