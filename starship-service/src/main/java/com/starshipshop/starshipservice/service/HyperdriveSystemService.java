package com.starshipshop.starshipservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.starshipshop.starshipservice.common.exception.ResourceNotFoundException;
import com.starshipshop.starshipservice.domain.hyperdriveSystem.HyperdriveSystemInput;
import com.starshipshop.starshipservice.domain.hyperdriveSystem.HyperdriveSystemOutput;
import com.starshipshop.starshipservice.repository.HyperdriveSystemRepository;
import com.starshipshop.starshipservice.repository.jpa.HyperdriveSystem;
import com.starshipshop.starshipservice.service.mapper.StarshipShopMapper;
import com.starshipshop.starshipservice.service.mapper.converter.IdToHashConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HyperdriveSystemService {

	private final ManufacturerService manufacturerService;
	private final HyperdriveSystemRepository hyperdriveSystemRepository;
	private final StarshipShopMapper mapper;
	private final IdToHashConverter idToHashConverter;
	private final EntityManager entityManager;

	private List<HyperdriveSystem> getHyperdriveSystems() {
		return hyperdriveSystemRepository.findAll();
	}

	public List<HyperdriveSystemOutput> getHyperdriveSystemsOutput() {
		return this.getHyperdriveSystems()
				.stream()
				.map(mapper::toHyperdriveSystemOutput)
				.collect(Collectors.toList());
	}

	private Optional<HyperdriveSystem> getHyperdriveSystemById(final Long id) throws ResourceNotFoundException {
		return hyperdriveSystemRepository.findById(id);
	}

	public HyperdriveSystemOutput getHyperdriveSystemOutputById(final Long id) {
		return mapper.toHyperdriveSystemOutput(
				this.getHyperdriveSystemById(id).orElseThrow(() -> new ResourceNotFoundException(
						"Cannot retrieve hyperdriveSystem with the given id: " + idToHashConverter.convert(id))));
	}

	@Transactional
	public HyperdriveSystemOutput createHyperdriveSystem(final HyperdriveSystemInput hsi) {
		this.checkHyperdriveSystemInput(hsi);
		// Check Manufacturer
		if (hsi.getManufacturerId() != null) {
			manufacturerService.checkManufacturerExist(hsi.getManufacturerId());
		}
		HyperdriveSystem toSave = mapper.toHyperdriveSystem(hsi);
		// Avoid hibernate save errors
		if (toSave.getManufacturer() != null && toSave.getManufacturer().getId() == null) {
			toSave.setManufacturer(null);
		}
		HyperdriveSystem saved = hyperdriveSystemRepository.save(toSave);
		// Refresh entity to load Manufacturer fields
		if (saved.getManufacturer() != null && saved.getManufacturer().getId() != null) {
			entityManager.refresh(saved);
		}
		return mapper.toHyperdriveSystemOutput(saved);
	}

	@Transactional
	public HyperdriveSystemOutput updateHyperdriveSystem(final Long id, final HyperdriveSystemInput hsi) {
		Assert.notNull(id, String.format("id cannot be null."));
		this.checkHyperdriveSystemInput(hsi);
		this.checkHyperdriveSystemExist(id);
		// Check Manufacturer
		if (hsi.getManufacturerId() != null) {
			manufacturerService.checkManufacturerExist(hsi.getManufacturerId());
		}

		HyperdriveSystem toSave = mapper.toHyperdriveSystem(hsi);
		toSave.setId(id);
		// Avoid hibernate save errors
		if (toSave.getManufacturer() != null && toSave.getManufacturer().getId() == null) {
			toSave.setManufacturer(null);
		}
		HyperdriveSystem saved = hyperdriveSystemRepository.save(toSave);
		// Refresh entity to load Manufacturer fields
		if (saved.getManufacturer() != null && saved.getManufacturer().getId() != null) {
			entityManager.refresh(saved);
		}
		return mapper.toHyperdriveSystemOutput(saved);
	}

	@Transactional
	public void deleteHyperdriveSystem(final Long id) {
		Assert.notNull(id, String.format("id cannot be null."));
		HyperdriveSystem hyperdriveSystemToDelete = this.checkHyperdriveSystemExist(id);
		hyperdriveSystemRepository.delete(hyperdriveSystemToDelete);
	}

	public void checkHyperdriveSystemInput(HyperdriveSystemInput hsri) {
		Assert.notNull(hsri, String.format("HyperdriveSystem cannot be null."));
		Assert.notNull(hsri.getName(), String.format("Name of HyperdriveSystem cannot be null."));
		Assert.hasText(hsri.getName(), String.format("Name of HyperdriveSystem cannot be empty."));
	}

	public HyperdriveSystem checkHyperdriveSystemExist(Long id) {
		return hyperdriveSystemRepository.findById(id)
				.orElseThrow(
						() -> new ResourceNotFoundException("HyperdriveSystem doesn't exist with this id: "
								+ idToHashConverter.convert(id)));
	}
}
