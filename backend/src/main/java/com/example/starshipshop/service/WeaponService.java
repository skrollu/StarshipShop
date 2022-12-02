package com.example.starshipshop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.starshipshop.common.exception.ResourceNotFoundException;
import com.example.starshipshop.domain.WeaponDto;
import com.example.starshipshop.domain.WeaponInput;
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

	public List<Weapon> getWeapons() {
		return weaponRepository.findAll();
	}

	public List<WeaponDto> getWeaponsDto() {
		return this	.getWeapons()
					.stream()
					.map(mapper::toWeaponDto)
					.collect(Collectors.toList());
	}

	public Optional<Weapon> getWeaponById(final Long id) {
		return weaponRepository.findById(id);
	}

	public Optional<WeaponDto> getWeaponsDtoById(final Long id) {
		return this	.getWeaponById(id)
					.map(mapper::toWeaponDto);
	}

	public WeaponDto createWeapon(final WeaponInput wi) {
		this.checkWeaponRequestInput(wi);
		// Check Manufacturer
		if(wi.getManufacturer() != null) {
			this.manufacturerService.checkManufacturerDto(wi.getManufacturer());
			this.manufacturerService.checkManufacturerExist(wi.getManufacturer().getId());
		}
		
		return mapper.toWeaponDto(weaponRepository.save(mapper.fromWeaponRequestInput(wi)));
	}

	public WeaponDto updateWeapon(final Long id, final WeaponInput wi) {
		Assert.notNull(id, String.format("id cannot be null."));		
		this.checkWeaponRequestInput(wi);
		this.checkWeaponExist(id);

		// Check Manufacturer
		if(wi.getManufacturer() != null) {
			this.manufacturerService.checkManufacturerDto(wi.getManufacturer());
			this.manufacturerService.checkManufacturerExist(wi.getManufacturer().getId());
		}

		Weapon w = mapper.fromWeaponRequestInput(wi);
		w.setId(id);
		return mapper.toWeaponDto(weaponRepository.save(w));
	}

	public void deleteWeapon(final Long id) {
		Assert.notNull(id, String.format("id cannot be null."));
		Weapon weaponToDelete = this.checkWeaponExist(id);
		weaponRepository.delete(weaponToDelete);
	}

	public void checkWeaponRequestInput(WeaponInput wi) {
		Assert.notNull(wi, String.format("Weapon cannot be null."));
		Assert.notNull(wi.getName(), String.format("Name of Weapon cannot be null."));
		Assert.hasText(wi.getName(), String.format("Name of Weapon cannot be empty."));
	}

	public void checkWeaponDto(WeaponDto dto) {
		Assert.notNull(dto, String.format("Weapon cannot be null."));
		Assert.notNull(dto.getId(), String.format("Id of Weapon cannot be null."));
		Assert.notNull(dto.getName(), String.format("Name of Weapon cannot be null."));
		Assert.hasText(dto.getName(), String.format("Name of Weapon cannot be empty."));
	}
	
	public Weapon checkWeaponExist(Long id) {
		return weaponRepository	.findById(id)
								.orElseThrow(
										() -> new ResourceNotFoundException("Weapon doesn't exist with this id: "
												+ idToHashConverter.convert(id)));
	}
}
