package com.example.starshipShop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.starshipShop.dto.ManufacturerDto;
import com.example.starshipShop.dto.WeaponDto;
import com.example.starshipShop.dto.WeaponRequestInput;
import com.example.starshipShop.exception.ResourceNotFoundException;
import com.example.starshipShop.jpa.Weapon;
import com.example.starshipShop.mapper.StarshipShopMapper;
import com.example.starshipShop.mapper.converter.IdToHashConverter;
import com.example.starshipShop.repository.WeaponRepository;

import lombok.Data;

@Data
@Service
public class WeaponService {

	@Autowired
	private WeaponRepository weaponRepository;

	@Autowired
	private ManufacturerService manufacturerService;

	@Autowired
	private final StarshipShopMapper mapper;

	public List<Weapon> getWeapons() {
		return weaponRepository.findAll();
	}

	public List<WeaponDto> getWeaponsDto() {
		return this	.getWeapons()
					.stream()
					.map(w -> mapper.toWeaponDto(w))
					.collect(Collectors.toList());
	}

	public Optional<Weapon> getWeaponById(final Long id) {
		return weaponRepository.findById(id);
	}

	public Optional<WeaponDto> getWeaponsDtoById(final Long id) {
		return this	.getWeaponById(id)
					.map(w -> mapper.toWeaponDto(w));
	}

	public WeaponDto createWeapon(final WeaponRequestInput wri) {
		Assert.notNull(wri.getName(), String.format("Weapon name cannot be null."));
		Assert.hasText(wri.getName(), String.format("Weapon name cannot be empty."));
		Assert.notNull(wri.getManufacturer(), String.format("Manufacturer cannot be null."));
		Assert.notNull(wri	.getManufacturer()
							.getId(),
				String.format("Manufacturer's id cannot be null."));
		Assert.hasText(wri	.getManufacturer()
							.getName(),
							String.format("Manufacturer's name cannot be empty."));

		this.manufacturerService	.getManufacturerById(wri	.getManufacturer()
																					.getId())
														.orElseThrow(() -> new ResourceNotFoundException(
																"The given Manufacturer doesn't exist with this id: "
																		+ IdToHashConverter.convertToHash(
																				wri	.getManufacturer()
																					.getId())));
		
		return mapper.toWeaponDto(weaponRepository.save(mapper.fromWeaponRequestInput(wri)));
	}

		public WeaponDto updateWeapon(final Long id, final WeaponRequestInput wri) {
		Assert.notNull(wri.getName(), String.format("Weapon name cannot be null."));
		Assert.hasText(wri.getName(), String.format("Weapon name cannot be empty."));
		Assert.notNull(wri.getManufacturer(), String.format("Manufacturer cannot be null."));
		Assert.notNull(wri	.getManufacturer()
							.getId(),
				String.format("Manufacturer's id cannot be null."));
		Assert.hasText(wri	.getManufacturer()
							.getName(),
				String.format("Manufacturer's name cannot be empty."));

		this.manufacturerService	.getManufacturerById(wri	.getManufacturer()
																					.getId())
														.orElseThrow(() -> new ResourceNotFoundException(
																"The given Manufacturer doesn't exist with this id: "
																		+ IdToHashConverter.convertToHash(
																				wri	.getManufacturer()
																					.getId())));
		Weapon w = mapper.fromWeaponRequestInput(wri);
		w.setId(id);
		return mapper.toWeaponDto(weaponRepository.save(w));
	}

	public void deleteWeapon(final Long id) {
					Assert.notNull(id, String.format("id cannot be null."));
		Weapon weaponToDelete = this.getWeaponById(id)
									.orElseThrow(() -> new ResourceNotFoundException(
											"Weapon doesn't exist with this id " + IdToHashConverter.convertToHash(
																							id)));
		weaponRepository.delete(weaponToDelete);
	}
}
