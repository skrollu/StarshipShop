package com.example.starshipShop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.example.starshipShop.dto.StarshipDto;
import com.example.starshipShop.dto.StarshipRequestInput;
import com.example.starshipShop.exception.ResourceNotFoundException;
import com.example.starshipShop.repository.StarshipRepository;
import com.example.starshipShop.repository.jpa.Starship;
import com.example.starshipShop.service.mapper.StarshipShopMapper;
import com.example.starshipShop.service.mapper.converter.IdToHashConverter;
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
					.map(s -> mapper.toStarshipDto(s))
					.collect(Collectors.toList());
	}

	public Optional<Starship> geStarshipById(final Long id) {
		return starshipRepository.findById(id);
	}

	public Optional<StarshipDto> getStarshipDtoById(final Long id) {
		return this	.geStarshipById(id)
					.map(w -> mapper.toStarshipDto(w));
	}

	public StarshipDto createStarship(final StarshipRequestInput sri) {
		this.checkStarshipRequestInput(sri);
		// Check Manufacturer
		if(sri.getManufacturer() != null) {
			this.manufacturerService.checkManufacturerDto(sri.getManufacturer());
			this.manufacturerService.checkManufacturerExist(sri.getManufacturer().getId());
		}

		// Check HyperdriveSystem
		if(sri.getHyperdriveSystem() != null) {
			this.hyperdriveSystemService.checkHyperdriveSystemDto(sri.getHyperdriveSystem());
			this.hyperdriveSystemService.checkHyperdriveSystemExist(sri.getHyperdriveSystem().getId());
		}

		// Check Weapons
		if (sri.getWeapons() != null && !sri.getWeapons().isEmpty()) {
			sri	.getWeapons()
				.forEach(w -> {
					this.weaponService.checkWeaponDto(w);
					this.weaponService.checkWeaponExist(w.getId());
				});
		}

		return mapper.toStarshipDto(starshipRepository.save(mapper.fromStarshipRequestInput(sri)));
	}

	public StarshipDto updateStarship(final Long id, final StarshipRequestInput sri) {
		// Check Manufacturer
		this.manufacturerService.checkManufacturerDto(sri.getManufacturer());
		this.manufacturerService.checkManufacturerExist(sri.getManufacturer().getId());

		// Check HyperdriveSystem
		if(sri.getHyperdriveSystem() != null) {
			this.hyperdriveSystemService.checkHyperdriveSystemDto(sri.getHyperdriveSystem());
			this.hyperdriveSystemService.checkHyperdriveSystemExist(sri.getHyperdriveSystem().getId());
		}

		// Check Weapons
		if (!sri.getWeapons().isEmpty()) {
			sri	.getWeapons()
				.forEach(w -> {
					this.weaponService.checkWeaponDto(w);
					this.weaponService.checkWeaponExist(w.getId());
				});
		}

        Starship s = mapper.fromStarshipRequestInput(sri);
        s.setId(id);
		return mapper.toStarshipDto(starshipRepository.save(s));
	}

	public void deleteStarship(final Long id) {
        Assert.notNull(id, String.format("id cannot be null."));
		Starship starshipToDelete = this.checkStarshipExist(id);
		starshipRepository.delete(starshipToDelete);
	}

	public void checkStarshipRequestInput(StarshipRequestInput sri) {
		Assert.notNull(sri, String.format("Starship cannot be null."));
		Assert.notNull(sri.getName(), String.format("Name of Starship cannot be null."));
		Assert.hasText(sri.getName(), String.format("Name of Starship cannot be empty."));
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