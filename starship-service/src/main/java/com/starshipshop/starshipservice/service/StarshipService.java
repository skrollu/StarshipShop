package com.starshipshop.starshipservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.starshipshop.starshipservice.common.exception.ResourceNotFoundException;
import com.starshipshop.starshipservice.domain.starship.StarshipInput;
import com.starshipshop.starshipservice.domain.starship.StarshipOutput;
import com.starshipshop.starshipservice.repository.StarshipRepository;
import com.starshipshop.starshipservice.repository.jpa.Starship;
import com.starshipshop.starshipservice.repository.jpa.Weapon;
import com.starshipshop.starshipservice.service.mapper.StarshipShopMapper;
import com.starshipshop.starshipservice.service.mapper.converter.IdToHashConverter;

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
	private final EntityManager entityManager;

	private List<Starship> getStarships() {
		return starshipRepository.findAll();
	}

	public List<StarshipOutput> getStarshipsDto() {
		return this.getStarships()
				.stream()
				.map(mapper::toStarshipOutput)
				.collect(Collectors.toList());
	}

	private Optional<Starship> geStarshipById(final Long id) {
		return starshipRepository.findById(id);
	}

	public StarshipOutput getStarshipOutputById(final Long id) {
		return mapper.toStarshipOutput(this.geStarshipById(id).orElseThrow(() -> new ResourceNotFoundException(
				"Cannot retrieve hyperdriveSystem with the given id: " + idToHashConverter.convert(id))));
	}

	@Transactional
	public StarshipOutput createStarship(@Valid final StarshipInput si) {
		this.checkStarshipInput(si);
		// Check Manufacturer
		if (si.getManufacturerId() != null) {
			manufacturerService.checkManufacturerExist(si.getManufacturerId());
		}
		// Check HyperdriveSystem
		if (si.getHyperdriveSystemId() != null) {
			hyperdriveSystemService.checkHyperdriveSystemExist(si.getHyperdriveSystemId());
		}
		// Check Weapons
		if (si.getWeapons() != null && !si.getWeapons().isEmpty()) {
			si.getWeapons()
					.forEach(w -> {
						if (w.getWeaponId() != null) {
							this.weaponService.checkWeaponExist(w.getWeaponId());
						}
					});
		}
		Starship toSave = mapper.fromStarshipInput(si);
		// Avoid hibernate save errors
		if (toSave.getManufacturer() != null && toSave.getManufacturer().getId() == null) {
			toSave.setManufacturer(null);
		}
		if (toSave.getHyperdriveSystem() != null && toSave.getHyperdriveSystem().getId() == null) {
			toSave.setHyperdriveSystem(null);
		}
		if (toSave.getWeapons() != null) {
			if (!toSave.getWeapons().isEmpty()) {
				toSave.getWeapons().forEach(w -> {
					if (w.getId() == null) {
						w = null;
					}
				});
			} else {
				toSave.setWeapons(null);
			}
		}
		Starship saved = starshipRepository.save(toSave);
		// Refresh entity to load nested fields
		// TODO manage case of empty object : {} given as StarshipWeaponInput
		boolean refresh = false;
		if (saved.getManufacturer() != null && saved.getManufacturer().getId() != null
				|| saved.getHyperdriveSystem() != null && saved.getHyperdriveSystem().getId() != null) {
			refresh = true;
		}
		if (!refresh && saved.getWeapons() != null && !saved.getWeapons().isEmpty()) {
			for (Weapon w : saved.getWeapons()) {
				if (w.getId() != null) {
					refresh = true;
				}
			}
		}
		if (refresh) {
			entityManager.flush();
			entityManager.refresh(saved);
		}
		return mapper.toStarshipOutput(saved);
	}

	@Transactional
	public StarshipOutput updateStarship(final Long id, @Valid final StarshipInput si) {
		Assert.notNull(id, String.format("id cannot be null."));
		this.checkStarshipInput(si);
		// Check Manufacturer
		if (si.getManufacturerId() != null) {
			manufacturerService.checkManufacturerExist(si.getManufacturerId());
		}
		// Check HyperdriveSystem
		if (si.getHyperdriveSystemId() != null) {
			hyperdriveSystemService.checkHyperdriveSystemExist(si.getHyperdriveSystemId());
		}
		// Check Weapons
		if (si.getWeapons() != null && !si.getWeapons().isEmpty()) {
			si.getWeapons()
					.forEach(w -> {
						if (w.getWeaponId() != null) {
							this.weaponService.checkWeaponExist(w.getWeaponId());
						}
					});
		}
		Starship toSave = mapper.fromStarshipInput(si);
		toSave.setId(id);
		// Avoid hibernate save errors
		if (toSave.getManufacturer() != null && toSave.getManufacturer().getId() == null) {
			toSave.setManufacturer(null);
		}
		if (toSave.getHyperdriveSystem() != null && toSave.getHyperdriveSystem().getId() == null) {
			toSave.setHyperdriveSystem(null);
		}
		if (toSave.getWeapons() != null) {
			if (!toSave.getWeapons().isEmpty()) {
				toSave.getWeapons().forEach(w -> {
					if (w.getId() == null) {
						w = null;
					}
				});
			} else {
				toSave.setWeapons(null);
			}
		}
		Starship saved = starshipRepository.save(toSave);
		// Refresh entity to load nested fields
		// TODO manage case of empty object : {} given as StarshipWeaponInput
		boolean refresh = false;
		if (saved.getManufacturer() != null && saved.getManufacturer().getId() != null
				|| saved.getHyperdriveSystem() != null && saved.getHyperdriveSystem().getId() != null) {
			refresh = true;
		}
		if (!refresh && saved.getWeapons() != null && !saved.getWeapons().isEmpty()) {
			for (Weapon w : saved.getWeapons()) {
				if (w.getId() != null) {
					refresh = true;
				}
			}
		}
		if (refresh) {
			entityManager.flush();
			entityManager.refresh(saved);
		}
		return mapper.toStarshipOutput(saved);
	}

	@Transactional
	public void deleteStarship(final Long id) {
		Assert.notNull(id, String.format("id cannot be null."));
		Starship starshipToDelete = this.checkStarshipExist(id);
		starshipRepository.delete(starshipToDelete);
	}

	public void checkStarshipInput(StarshipInput si) {
		Assert.notNull(si, String.format("Starship cannot be null."));
		Assert.notNull(si.getName(), String.format("Name of Starship cannot be null."));
		Assert.hasText(si.getName(), String.format("Name of Starship cannot be empty."));
	}

	public Starship checkStarshipExist(Long id) {
		return starshipRepository.findById(id)
				.orElseThrow(
						() -> new ResourceNotFoundException("Starship doesn't exist with this id: "
								+ idToHashConverter.convert(id)));
	}
}