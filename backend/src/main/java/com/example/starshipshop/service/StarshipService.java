package com.example.starshipshop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.starshipshop.common.exception.ResourceNotFoundException;
import com.example.starshipshop.domain.StarshipDto;
import com.example.starshipshop.domain.StarshipInput;
import com.example.starshipshop.repository.StarshipRepository;
import com.example.starshipshop.repository.jpa.Starship;
import com.example.starshipshop.service.mapper.StarshipShopMapper;
import com.example.starshipshop.service.mapper.converter.IdToHashConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StarshipService {

	private final ManufacturerService manufacturerService;

	private final HyperdriveSystemService hyperdriveSystemService;

	private final WeaponService weaponService;
	
	private final StarshipRepository starshipRepository;

	private final StarshipShopMapper mapper;

	private final IdToHashConverter idToHashConverter;

	public List<Starship> getStarships() {
		return starshipRepository.findAll();
	}

	public List<StarshipDto> getStarshipsDto() {
		return this	.getStarships()
					.stream()
					.map(mapper::toStarshipDto)
					.collect(Collectors.toList());
	}

	public Optional<Starship> geStarshipById(final Long id) {
		return starshipRepository.findById(id);
	}

	public Optional<StarshipDto> getStarshipDtoById(final Long id) {
		return this	.geStarshipById(id)
					.map(mapper::toStarshipDto);
	}

	public StarshipDto createStarship(final StarshipInput si) {
		this.checkStarshipRequestInput(si);
		// Check Manufacturer
		if(si.getManufacturer() != null) {
			this.manufacturerService.checkManufacturerDto(si.getManufacturer());
			this.manufacturerService.checkManufacturerExist(si.getManufacturer().getId());
		}

		// Check HyperdriveSystem
		if(si.getHyperdriveSystem() != null) {
			this.hyperdriveSystemService.checkHyperdriveSystemDto(si.getHyperdriveSystem());
			this.hyperdriveSystemService.checkHyperdriveSystemExist(si.getHyperdriveSystem().getId());
		}

		// Check Weapons
		if (si.getWeapons() != null && !si.getWeapons().isEmpty()) {
			si	.getWeapons()
				.forEach(w -> {
					this.weaponService.checkWeaponDto(w);
					this.weaponService.checkWeaponExist(w.getId());
				});
		}

		return mapper.toStarshipDto(starshipRepository.save(mapper.fromStarshipRequestInput(si)));
	}

	public StarshipDto updateStarship(final Long id, final StarshipInput si) {
		// Check Manufacturer
		this.manufacturerService.checkManufacturerDto(si.getManufacturer());
		this.manufacturerService.checkManufacturerExist(si.getManufacturer().getId());

		// Check HyperdriveSystem
		if(si.getHyperdriveSystem() != null) {
			this.hyperdriveSystemService.checkHyperdriveSystemDto(si.getHyperdriveSystem());
			this.hyperdriveSystemService.checkHyperdriveSystemExist(si.getHyperdriveSystem().getId());
		}

		// Check Weapons
		if (!si.getWeapons().isEmpty()) {
			si	.getWeapons()
				.forEach(w -> {
					this.weaponService.checkWeaponDto(w);
					this.weaponService.checkWeaponExist(w.getId());
				});
		}

        Starship s = mapper.fromStarshipRequestInput(si);
        s.setId(id);
		return mapper.toStarshipDto(starshipRepository.save(s));
	}

	public void deleteStarship(final Long id) {
        Assert.notNull(id, String.format("id cannot be null."));
		Starship starshipToDelete = this.checkStarshipExist(id);
		starshipRepository.delete(starshipToDelete);
	}

	public void checkStarshipRequestInput(StarshipInput si) {
		Assert.notNull(si, String.format("Starship cannot be null."));
		Assert.notNull(si.getName(), String.format("Name of Starship cannot be null."));
		Assert.hasText(si.getName(), String.format("Name of Starship cannot be empty."));
	}

	public void checkStarshipDto(StarshipDto dto) {
		Assert.notNull(dto, String.format("Starship cannot be null."));
		Assert.notNull(dto.getId(), String.format("Id of Starship cannot be null."));
		Assert.notNull(dto.getName(), String.format("Name of Starship cannot be null."));
		Assert.hasText(dto.getName(), String.format("Name of Starship cannot be empty."));
	}
	
	public Starship checkStarshipExist(Long id) {
		return starshipRepository.findById(id)
								.orElseThrow(
										() -> new ResourceNotFoundException("Starship doesn't exist with this id: "
												+ idToHashConverter.convert(id)));
	}
}