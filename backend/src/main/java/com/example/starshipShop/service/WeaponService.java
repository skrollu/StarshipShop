package com.example.starshipshop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.example.starshipshop.domain.WeaponDto;
import com.example.starshipshop.domain.WeaponRequestInput;
import com.example.starshipshop.exception.ResourceNotFoundException;
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
		this.checkWeaponRequestInput(wri);
		// Check Manufacturer
		if(wri.getManufacturer() != null) {
			this.manufacturerService.checkManufacturerDto(wri.getManufacturer());
			this.manufacturerService.checkManufacturerExist(wri.getManufacturer().getId());
		}
		
		return mapper.toWeaponDto(weaponRepository.save(mapper.fromWeaponRequestInput(wri)));
	}

	public WeaponDto updateWeapon(final Long id, final WeaponRequestInput wri) {
		Assert.notNull(id, String.format("id cannot be null."));		
		this.checkWeaponRequestInput(wri);
		this.checkWeaponExist(id);

		// Check Manufacturer
		if(wri.getManufacturer() != null) {
			this.manufacturerService.checkManufacturerDto(wri.getManufacturer());
			this.manufacturerService.checkManufacturerExist(wri.getManufacturer().getId());
		}

		Weapon w = mapper.fromWeaponRequestInput(wri);
		w.setId(id);
		return mapper.toWeaponDto(weaponRepository.save(w));
	}

	public void deleteWeapon(final Long id) {
		Assert.notNull(id, String.format("id cannot be null."));
		Weapon weaponToDelete = this.checkWeaponExist(id);
		weaponRepository.delete(weaponToDelete);
	}

	public void checkWeaponRequestInput(WeaponRequestInput wri) {
		Assert.notNull(wri, String.format("Weapon cannot be null."));
		Assert.notNull(wri.getName(), String.format("Name of Weapon cannot be null."));
		Assert.hasText(wri.getName(), String.format("Name of Weapon cannot be empty."));
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
