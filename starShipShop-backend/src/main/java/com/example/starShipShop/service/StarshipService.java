package com.example.starshipShop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.starshipShop.dto.StarshipDto;
import com.example.starshipShop.dto.StarshipRequestInput;
import com.example.starshipShop.exception.ResourceNotFoundException;
import com.example.starshipShop.jpa.Starship;
import com.example.starshipShop.mapper.StarshipShopMapper;
import com.example.starshipShop.mapper.converter.IdToHashConverter;
import com.example.starshipShop.repository.StarshipRepository;

import lombok.Data;

@Data
@Service
public class StarshipService {

	@Autowired
	private ManufacturerService manufacturerService;

	@Autowired
	private HyperdriveSystemService hyperdriveSystemService;

	@Autowired
	private WeaponService weaponService;

	@Autowired
	private final StarshipShopMapper mapper;

	@Autowired
	private StarshipRepository starshipRepository;

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
		// Check Manufacturer
		Assert.notNull(sri.getName(), String.format("Starship's name cannot be null."));
		Assert.hasText(sri.getName(), String.format("Starship's name cannot be empty."));
		Assert.notNull(sri.getManufacturer(), String.format("Manufacturer cannot be null."));
		Assert.notNull(sri	.getManufacturer()
							.getId(),
				String.format("Manufacturer's id cannot be null."));
		Assert.hasText(sri	.getManufacturer()
							.getName(),
				String.format("Manufacturer's name cannot be empty."));
		this.manufacturerService.getManufacturerById(sri.getManufacturer()
														.getId())
								.orElseThrow(() -> new ResourceNotFoundException(
										"The given Manufacturer of Starship doesn't exist with this id: "
												+ IdToHashConverter.convertToHash(
														sri	.getManufacturer()
															.getId())));

		// Check HyperdriveSystem
		Assert.notNull(sri.getName(), String.format("HyperdriveSystem name cannot be null."));
		Assert.hasText(sri.getName(), String.format("HyperdriveSystem name cannot be empty."));
		Assert.notNull(sri.getHyperdriveSystem(), String.format("HyperdriveSystem cannot be null."));
		Assert.notNull(sri	.getHyperdriveSystem()
							.getId(),
				String.format("HyperdriveSystem's id cannot be null."));
		Assert.hasText(sri	.getHyperdriveSystem()
							.getName(),
				String.format("HyperdriveSystem's name cannot be empty."));

		this.hyperdriveSystemService.getHyperdriveSystemById(sri.getHyperdriveSystem()
																.getId())
									.orElseThrow(() -> new ResourceNotFoundException(
											"The given HyperdriveSystem of Starship doesn't exist with this id: "
													+ IdToHashConverter.convertToHash(
															sri	.getHyperdriveSystem()
																.getId())));

		// Check Weapon
		if (!sri.getWeapons()
				.isEmpty()) {
			sri	.getWeapons()
				.forEach(w -> {
					Assert.notNull(w, "Weapon cannot be null.");
					Assert.notNull(w.getId(), "Weapon's cannot be null.");
					Assert.notNull(w.getName(), "Weapon's name cannot be null.");
					Assert.notNull(w.getName(), "Weapon's name cannot be empty.");
					this.weaponService	.getWeaponById(w.getId())
										.orElseThrow(() -> new ResourceNotFoundException(
												"The given Weapon of Starship doesn't exist with this id: "
														+ IdToHashConverter.convertToHash(
																w.getId())));
				});
		}

		return mapper.toStarshipDto(starshipRepository.save(mapper.fromStarshipRequestInput(sri)));
	}
	public StarshipDto updateStarship(final Long id, final StarshipRequestInput sri) {
		// Check Manufacturer
		Assert.notNull(sri.getName(), String.format("Starship's name cannot be null."));
		Assert.hasText(sri.getName(), String.format("Starship's name cannot be empty."));
		Assert.notNull(sri.getManufacturer(), String.format("Manufacturer cannot be null."));
		Assert.notNull(sri	.getManufacturer()
							.getId(),
				String.format("Manufacturer's id cannot be null."));
		Assert.hasText(sri	.getManufacturer()
							.getName(),
				String.format("Manufacturer's name cannot be empty."));
		this.manufacturerService.getManufacturerById(sri.getManufacturer()
														.getId())
								.orElseThrow(() -> new ResourceNotFoundException(
										"The given Manufacturer of Starship doesn't exist with this id: "
												+ IdToHashConverter.convertToHash(
														sri	.getManufacturer()
															.getId())));

		// Check HyperdriveSystem
		Assert.notNull(sri.getName(), String.format("HyperdriveSystem name cannot be null."));
		Assert.hasText(sri.getName(), String.format("HyperdriveSystem name cannot be empty."));
		Assert.notNull(sri.getHyperdriveSystem(), String.format("HyperdriveSystem cannot be null."));
		Assert.notNull(sri	.getHyperdriveSystem()
							.getId(),
				String.format("HyperdriveSystem's id cannot be null."));
		Assert.hasText(sri	.getHyperdriveSystem()
							.getName(),
				String.format("HyperdriveSystem's name cannot be empty."));

		this.hyperdriveSystemService.getHyperdriveSystemById(sri.getHyperdriveSystem()
																.getId())
									.orElseThrow(() -> new ResourceNotFoundException(
											"The given HyperdriveSystem of Starship doesn't exist with this id: "
													+ IdToHashConverter.convertToHash(
															sri	.getHyperdriveSystem()
																.getId())));

		// Check Weapon
		if (!sri.getWeapons()
				.isEmpty()) {
			sri	.getWeapons()
				.forEach(w -> {
					Assert.notNull(w, "Weapon cannot be null.");
					Assert.notNull(w.getId(), "Weapon's cannot be null.");
					Assert.notNull(w.getName(), "Weapon's name cannot be null.");
					Assert.notNull(w.getName(), "Weapon's name cannot be empty.");
					this.weaponService	.getWeaponById(w.getId())
										.orElseThrow(() -> new ResourceNotFoundException(
												"The given Weapon of Starship doesn't exist with this id: "
														+ IdToHashConverter.convertToHash(
																w.getId())));
				});
		}

        Starship s = mapper.fromStarshipRequestInput(sri);
        s.setId(id);
		return mapper.toStarshipDto(starshipRepository.save(s));
	}

	public void deleteStarship(final Long id) {
        Assert.notNull(id, String.format("id cannot be null."));
		Starship starshipToDelete = this.geStarshipById(id)
										.orElseThrow(() -> new ResourceNotFoundException(
												"Starship doesn't exist with this id " + IdToHashConverter.convertToHash(
																							id)));
		starshipRepository.delete(starshipToDelete);
	}
}