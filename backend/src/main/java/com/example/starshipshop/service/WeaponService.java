package com.example.starshipshop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.example.starshipshop.common.exception.ResourceNotFoundException;
import com.example.starshipshop.domain.weapon.WeaponInput;
import com.example.starshipshop.domain.weapon.WeaponOutput;
import com.example.starshipshop.repository.WeaponRepository;
import com.example.starshipshop.repository.jpa.Weapon;
import com.example.starshipshop.service.mapper.StarshipShopMapper;
import com.example.starshipshop.service.mapper.converter.IdToHashConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WeaponService {

	private final WeaponRepository weaponRepository;
	private final ManufacturerService manufacturerService;
	private final StarshipShopMapper mapper;
	private final IdToHashConverter idToHashConverter;
	private final EntityManager entityManager;

	private List<Weapon> getWeapons() {
		return weaponRepository.findAll();
	}

	public List<WeaponOutput> getWeaponsOutput() {
		return this.getWeapons()
				.stream()
				.map(mapper::toWeaponOutput)
				.collect(Collectors.toList());
	}

	private Optional<Weapon> getWeaponById(final Long id) {
		return weaponRepository.findById(id);
	}

	public WeaponOutput getWeaponsOutputById(final Long id) {
		return this.getWeaponById(id)
				.map(mapper::toWeaponOutput)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Cannot retrieve weapon with the given id: " + idToHashConverter.convert(id)));
	}

	@Transactional
	public WeaponOutput createWeapon(final WeaponInput wi) {
		this.checkWeaponInput(wi);
		// Check Manufacturer
		if (wi.getManufacturerId() != null) {
			manufacturerService.checkManufacturerExist(wi.getManufacturerId());
		}
		Weapon toSave = mapper.fromWeaponInput(wi);
		// Avoid hibernate save errors
		if (toSave.getManufacturer() != null && toSave.getManufacturer().getId() == null) {
			toSave.setManufacturer(null);
		}
		Weapon saved = weaponRepository.save(toSave);
		// Refresh entity to load Manufacturer fields
		if (saved.getManufacturer() != null && saved.getManufacturer().getId() != null) {
			entityManager.refresh(saved);
		}
		return mapper.toWeaponOutput(saved);
	}

	@Transactional
	public WeaponOutput updateWeapon(final Long id, final WeaponInput wi) {
		Assert.notNull(id, String.format("id cannot be null."));
		this.checkWeaponInput(wi);
		this.checkWeaponExist(id);
		// Check Manufacturer
		if (wi.getManufacturerId() != null) {
			manufacturerService.checkManufacturerExist(wi.getManufacturerId());
		}

		Weapon toSave = mapper.fromWeaponInput(wi);
		toSave.setId(id);
		// Avoid hibernate save errors
		if (toSave.getManufacturer() != null && toSave.getManufacturer().getId() == null) {
			toSave.setManufacturer(null);
		}
		Weapon saved = weaponRepository.saveAndFlush(toSave);
		return mapper.toWeaponOutput(saved);
	}

	public void deleteWeapon(final Long id) {
		Assert.notNull(id, String.format("id cannot be null."));
		Weapon weaponToDelete = this.checkWeaponExist(id);
		weaponRepository.delete(weaponToDelete);
	}

	public void checkWeaponInput(WeaponInput wi) {
		Assert.notNull(wi, String.format("Weapon cannot be null."));
		Assert.notNull(wi.getName(), String.format("Name of Weapon cannot be null."));
		Assert.hasText(wi.getName(), String.format("Name of Weapon cannot be empty."));
	}

	public Weapon checkWeaponExist(Long id) {
		return weaponRepository.findById(id)
				.orElseThrow(
						() -> new ResourceNotFoundException("Weapon doesn't exist with this id: "
								+ idToHashConverter.convert(id)));
	}
}
