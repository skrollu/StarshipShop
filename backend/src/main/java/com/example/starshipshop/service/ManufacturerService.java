package com.example.starshipshop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.example.starshipshop.common.exception.ResourceNotFoundException;
import com.example.starshipshop.domain.manufacturer.ManufacturerInput;
import com.example.starshipshop.domain.manufacturer.ManufacturerOutput;
import com.example.starshipshop.repository.ManufacturerRepository;
import com.example.starshipshop.repository.jpa.Manufacturer;
import com.example.starshipshop.service.mapper.StarshipShopMapper;
import com.example.starshipshop.service.mapper.converter.IdToHashConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ManufacturerService {

	private final ManufacturerRepository manufacturerRepository;
	private final StarshipShopMapper mapper;
	private final IdToHashConverter idToHashConverter;

	private List<Manufacturer> getManufacturers() {
		return manufacturerRepository.findAll();
	}

	public List<ManufacturerOutput> getManufacturerOutput() {
		return this.getManufacturers()
				.stream()
				.map(mapper::toManufacturerOuput)
				.collect(Collectors.toList());
	}

	private Optional<Manufacturer> getManufacturerById(final Long id) {
		return manufacturerRepository.findById(id);
	}

	public ManufacturerOutput getManufacturerOuputById(final Long id) throws ResourceNotFoundException {
		return mapper.toManufacturerOuput(getManufacturerById(id).orElseThrow(() -> new ResourceNotFoundException(
				"Cannot retrieve manufacturer with the given id: " + idToHashConverter.convert(id))));
	}

	@Transactional
	public ManufacturerOutput createManufacturer(@Valid final ManufacturerInput mi) {
		this.checkManufacturerRequestInput(mi);
		return mapper.toManufacturerOuput(manufacturerRepository.save(mapper.fromManufaturerInput(mi)));
	}

	@Transactional
	public ManufacturerOutput updateManufacturer(final Long id, ManufacturerInput mi) {
		Assert.notNull(id, String.format("id cannot be null."));
		this.checkManufacturerRequestInput(mi);
		this.checkManufacturerExist(id);
		Manufacturer manufacturer = mapper.fromManufaturerInput(mi);
		manufacturer.setId(id);
		return mapper.toManufacturerOuput(manufacturerRepository.save(manufacturer));
	}

	@Transactional
	public void deleteManufacturer(final Long id) {
		Assert.notNull(id, String.format("id cannot be null."));
		Manufacturer manufacturerToDelete = this.checkManufacturerExist(id);
		manufacturerRepository.delete(manufacturerToDelete);
	}

	public void checkManufacturerRequestInput(ManufacturerInput mi) {
		Assert.notNull(mi, String.format("Manufacturer cannot be null."));
		Assert.notNull(mi.getName(), String.format("Name of Manufacturer cannot be null."));
		Assert.hasText(mi.getName(), String.format("Name of Manufacturer cannot be empty."));
	}

	public Manufacturer checkManufacturerExist(Long id) throws ResourceNotFoundException {
		return manufacturerRepository.findById(id)
				.orElseThrow(
						() -> new ResourceNotFoundException("Manufacturer doesn't exist with this id: "
								+ idToHashConverter.convert(id)));
	}
}