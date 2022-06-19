package com.example.starshipShop.service;

import static com.example.starshipShop.mapper.StarshipMapper.mapToEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.starshipShop.exception.ResourceNotFoundException;
import com.example.starshipShop.jpa.HyperdriveSystem;
import com.example.starshipShop.jpa.Starship;
import com.example.starshipShop.jpa.Weapon;
import com.example.starshipShop.repository.StarshipRepository;
import com.example.starshipShop.requestDto.StarshipRequestDTO;

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

	public Starship saveStarship(final StarshipRequestDTO starshipRequestDTO) {
		// Check nullpointer of Manufacturer
		if (starshipRequestDTO.getManufacturer() == null) {
			throw new NullPointerException("The given Manufacturer is null");
		}
		if (starshipRequestDTO	.getManufacturer()
								.getName() == null
				|| starshipRequestDTO	.getManufacturer()
										.getName()
										.isEmpty()) {
			throw new NullPointerException("The given Manufacturer's name is null or empty");
		}
		// Check nullpointer of HyperdriveSystem
		if (starshipRequestDTO.getHyperdriveSystem() == null) {
			throw new NullPointerException("The given HyperdriveSystem is null");
		}
		if (starshipRequestDTO	.getHyperdriveSystem()
								.getName() == null
				|| starshipRequestDTO	.getHyperdriveSystem()
										.getName()
										.isEmpty()) {
			throw new NullPointerException("The given HyperdriveSystem name is null or empty");
		}

		// Map the DTO into a Starship JPA Entity
		Starship starship = mapToEntity(starshipRequestDTO);

		// Search for the manufacturer by name and set it to starship
//		Manufacturer manufacturer = this.manufacturerService.getManufacturerByName(starship	.getManufacturer()
//																							.getName())
//															.orElseThrow(() -> new ResourceNotFoundException(
//																	"The given Manufacturer doesn't exist with this name: "
//																			+ starship	.getManufacturer()
//																						.getName()));

		// Search for the HyperdriveSystem by name and set it to starship
		HyperdriveSystem hyperdriveSystem = this.hyperdriveSystemService.getHyperdriveSystemByName(
				starship.getHyperdriveSystem()
						.getName())
																		.orElseThrow(
																				() -> new ResourceNotFoundException(
																						"The given HyperdriveSystem doesn't exist with this name: "
																								+ starship	.getHyperdriveSystem()
																											.getName()));

		// Search for the Weapon by name and set it to starship
		List<Weapon> weapons = new ArrayList<Weapon>();
		if (starshipRequestDTO.getWeapons() != null && !starshipRequestDTO	.getWeapons()
																			.isEmpty()) {
			for (Weapon w : starship.getWeapons()) {
				if (w.getName() != null && !w	.getName()
												.isEmpty()) {
					weapons.add(this.weaponService	.getWeaponByName(w.getName())
													.orElseThrow(() -> new ResourceNotFoundException(
															"The given Weapon doesn't exist with this name: "
																	+ w.getName())));
				}
			}
		}
//		starship.setManufacturer(manufacturer);
		starship.setHyperdriveSystem(hyperdriveSystem);
		starship.setWeapons(weapons);
		return starshipRepository.save(starship);
	}

	public Starship updateStarship(final Long id, StarshipRequestDTO starshipRequestDTO) {
		// Check nullpointer of Manufacturer
		if (starshipRequestDTO.getManufacturer() == null) {
			throw new NullPointerException("The given Manufacturer is null");
		}
		if (starshipRequestDTO	.getManufacturer()
								.getName() == null
				|| starshipRequestDTO	.getManufacturer()
										.getName()
										.isEmpty()) {
			throw new NullPointerException("The given Manufacturer's name is null or empty");
		}
		// Check nullpointer of HyperdriveSystem
		if (starshipRequestDTO.getHyperdriveSystem() == null) {
			throw new NullPointerException("The given HyperdriveSystem is null");
		}
		if (starshipRequestDTO	.getHyperdriveSystem()
								.getName() == null
				|| starshipRequestDTO	.getHyperdriveSystem()
										.getName()
										.isEmpty()) {
			throw new NullPointerException("The given HyperdriveSystem name is null or empty");
		}

		Starship starshipFromDb = this	.getStarshipById(id)
										.orElseThrow(() -> new ResourceNotFoundException(
												"Starship doesn't exist with this id " + id));
		Starship starshipToUpdate = mapToEntity(starshipRequestDTO, starshipFromDb);

		// Search for the manufacturer by name and set it to starship
//		Manufacturer manufacturer = this.manufacturerService.getManufacturerByName(starshipToUpdate	.getManufacturer()
//																									.getName())
//															.orElseThrow(() -> new ResourceNotFoundException(
//																	"The given Manufacturer doesn't exist with this name: "
//																			+ starshipToUpdate	.getManufacturer()
//																								.getName()));

		// Search for the HyperdriveSystem by name and set it to starship
		HyperdriveSystem hyperdriveSystem = this.hyperdriveSystemService.getHyperdriveSystemByName(
				starshipToUpdate.getHyperdriveSystem()
								.getName())
																		.orElseThrow(
																				() -> new ResourceNotFoundException(
																						"The given HyperdriveSystem doesn't exist with this name: "
																								+ starshipToUpdate	.getHyperdriveSystem()
																													.getName()));

		// Search for the Weapon by name and set it to starship
		List<Weapon> weapons = new ArrayList<Weapon>();
		if (starshipRequestDTO.getWeapons() != null && !starshipRequestDTO	.getWeapons()
																			.isEmpty()) {
			for (Weapon w : starshipToUpdate.getWeapons()) {
				if (w.getName() != null && !w	.getName()
												.isEmpty()) {
					weapons.add(this.weaponService	.getWeaponByName(w.getName())
													.orElseThrow(() -> new ResourceNotFoundException(
															"The given Weapon doesn't exist with this name: "
																	+ w.getName())));
				}
			}
		}
//		starshipToUpdate.setManufacturer(manufacturer);
		starshipToUpdate.setHyperdriveSystem(hyperdriveSystem);
		starshipToUpdate.setWeapons(weapons);
		return starshipRepository.save(starshipToUpdate);
	}

	public void deleteStarship(final Long id) {
		Starship starshipToDelete = this.getStarshipById(id)
										.orElseThrow(() -> new ResourceNotFoundException(
												"Starship doesn't exist with this id " + id));
		starshipRepository.delete(starshipToDelete);
	}
}