package com.example.starshipShop.service;

import static com.example.starshipShop.mapper.StarshipMapper.mapToEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.starshipShop.exception.ResourceNotFoundException;
import com.example.starshipShop.jpa.HyperdriveSystem;
import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.jpa.Starship;
import com.example.starshipShop.jpa.Weapon;
import com.example.starshipShop.repository.StarshipRepository;
import com.example.starshipShop.requestDto.StarshipRequestDTO;
import com.example.starshipShop.requestDto.WeaponRequestDTO;

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
	private StarshipRepository starshipRepository;

	public List<Starship> getStarships() {
		List<Starship> list = starshipRepository.findAll();
		return list;
	}

	public Optional<Starship> getStarshipById(final Long id) {
		return starshipRepository.findById(id);
	}

	public Starship saveStarship(final Starship starship) {
		Starship savedStarship = starshipRepository.save(starship);
		return savedStarship;
	}

	public Starship saveStarship(final StarshipRequestDTO starshipRequestDTO) {
		// Map the DTO into a Starship JPA Entity
		Starship starship = mapToEntity(starshipRequestDTO);
		if (starship == null) {
			// TODO throw error
		}
		// If the Manufacturer is present, then it by name and set it to starship
		if (starship.getManufacturer() != null && starship	.getManufacturer()
															.getName() != null) {
			Manufacturer manufacturer = this.manufacturerService.getManufacturerByName(starship	.getManufacturer()
																								.getName())
																.orElseThrow(() -> new ResourceNotFoundException(
																		"The given Manufacturer doesn't exist with this name: "
																				+ starship	.getManufacturer()
																							.getName()));
			starship.setManufacturer(manufacturer);
		}
		// If the HyperdriveSystem is present, then it by name and set it to starship
		if (starship.getHyperdriveSystem() != null && starship	.getHyperdriveSystem()
																.getName() != null) {
			HyperdriveSystem hyperdriveSystem = this.hyperdriveSystemService.getHyperdriveSystemByName(
					starship.getHyperdriveSystem()
							.getName())
																			.orElseThrow(
																					() -> new ResourceNotFoundException(
																							"The given HyperdriveSystem doesn't exist with this name: "
																									+ starship	.getHyperdriveSystem()
																												.getName()));
			starship.setHyperdriveSystem(hyperdriveSystem);
		}
		// If the Weapon are present, then it by name and set them to starship
		if (starship.getWeapons() != null && !starship	.getWeapons()
														.isEmpty()) {
			List<Weapon> weapons = new ArrayList<Weapon>();
			for (Weapon w : starship.getWeapons()) {
				if (w.getName() != null && !w	.getName()
												.isEmpty()) {
					weapons.add(this.weaponService	.getWeaponByName(w.getName())
													.orElseThrow(() -> new ResourceNotFoundException(
															"The given Weapon doesn't exist with this name: "
																	+ w.getName())));
				}
			}
			starship.setWeapons(weapons);
		}
		Starship savedStarship = starshipRepository.save(starship);
		return savedStarship;
	}

	public Starship updateStarship(final Long id, StarshipRequestDTO starshipRequestDTO) {
		Starship starshipFromDb = this	.getStarshipById(id)
										.orElseThrow(() -> new ResourceNotFoundException(
												"Starship doesn't exist with this id " + id));
		Starship starshipToUpdate = mapToEntity(starshipRequestDTO, starshipFromDb);
		if (starshipToUpdate == null) {
			// TODO throw error
		}
		if (starshipToUpdate.getManufacturer() != null && starshipToUpdate	.getManufacturer()
																			.getName() != null) {
			Manufacturer manufacturer = this.manufacturerService.getManufacturerByName(
					starshipToUpdate.getManufacturer()
									.getName())
																.orElseThrow(() -> new ResourceNotFoundException(
																		"The given Manufacturer doesn't exist with this name: "
																				+ starshipToUpdate	.getManufacturer()
																									.getName()));
			starshipToUpdate.setManufacturer(manufacturer);
		}
		if (starshipToUpdate.getHyperdriveSystem() != null && starshipToUpdate	.getHyperdriveSystem()
																				.getName() != null) {
			HyperdriveSystem hyperdriveSystem = this.hyperdriveSystemService.getHyperdriveSystemByName(
					starshipToUpdate.getHyperdriveSystem()
									.getName())
																			.orElseThrow(
																					() -> new ResourceNotFoundException(
																							"The given HyperdriveSystem doesn't exist with this name: "
																									+ starshipToUpdate	.getHyperdriveSystem()
																														.getName()));
			starshipToUpdate.setHyperdriveSystem(hyperdriveSystem);
		}
		if (starshipToUpdate.getWeapons() != null && !starshipToUpdate	.getWeapons()
																		.isEmpty()) {
			List<Weapon> weapons = new ArrayList<Weapon>();
			for (WeaponRequestDTO w : starshipRequestDTO.getWeapons()) {
				if (w.getName() != null && !w	.getName()
												.isEmpty()) {
					weapons.add(this.weaponService	.getWeaponByName(w.getName())
													.orElseThrow(() -> new ResourceNotFoundException(
															"The given Weapon doesn't exist with this name: "
																	+ w.getName())));
				}
			}
			starshipToUpdate.setWeapons(weapons);
		}
		Starship result = this.saveStarship(starshipToUpdate);
		return result;
	}

	public void deleteStarship(final Long id) {
		Starship starshipToDelete = this.getStarshipById(id)
										.orElseThrow(() -> new ResourceNotFoundException(
												"Starship doesn't exist with this id " + id));
		starshipRepository.delete(starshipToDelete);
	}
}