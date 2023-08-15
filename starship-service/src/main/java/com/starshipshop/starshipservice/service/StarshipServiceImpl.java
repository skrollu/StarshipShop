package com.starshipshop.starshipservice.service;

import com.starshipshop.starshipservice.adapter.StarshipAdapter;
import com.starshipshop.starshipservice.domain.Starship;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StarshipServiceImpl implements StarshipService {

	final StarshipAdapter starshipAdapter;

	public List<Starship> getStarshipByIdIn(List<Long> ids) {
		return this.starshipAdapter.getStarshipByIdIn(ids);
	}

	public Starship getStarshipById(Long id) {
		return this.starshipAdapter.getStarshipById(id);
	}
//	private List<Starship> getStarships() {
//		return starshipRepository.findAll();
//	}

//	public List<StarshipOutput> getStarshipsDto() {
//		return this.getStarships()
//				.stream()
//				.map(mapper::toStarshipOutput)
//				.collect(Collectors.toList());
//	}
//
//	private Optional<Starship> geStarshipById(final Long id) {
//		return starshipRepository.findById(id);
//	}
//
//	public StarshipOutput getStarshipOutputById(final Long id) {
//		return mapper.toStarshipOutput(this.geStarshipById(id).orElseThrow(() -> new ResourceNotFoundException(
//				"Cannot retrieve starship with the given id: " + idToHashConverter.convert(id))));
//	}
//
//	private List<Starship> geStarshipByIds(final List<Long> ids) {
//		return starshipRepository.findByIdIn(ids);
//	}
//
//	public List<StarshipOutput> getStarshipOutputByIds(final List<Long> ids) {
//		return this.geStarshipByIds(ids).stream()
//				.map(starship -> mapper.toStarshipOutput(starship))
//				.collect(Collectors.toList());
//	}
//
//	@Transactional
//	public StarshipOutput createStarship(@Valid final StarshipInput si) {
//		this.checkStarshipInput(si);
//		// Check Manufacturer
//		if (si.getManufacturerId() != null) {
//			manufacturerService.checkManufacturerExist(si.getManufacturerId());
//		}
//		// Check HyperdriveSystem
//		if (si.getHyperdriveSystemId() != null) {
//			hyperdriveSystemService.checkHyperdriveSystemExist(si.getHyperdriveSystemId());
//		}
//		// Check Weapons
//		if (si.getWeapons() != null && !si.getWeapons().isEmpty()) {
//			si.getWeapons()
//					.forEach(w -> {
//						if (w.getWeaponId() != null) {
//							this.weaponService.checkWeaponExist(w.getWeaponId());
//						}
//					});
//		}
//		Starship toSave = mapper.fromStarshipInput(si);
//		// Avoid hibernate save errors
//		if (toSave.getManufacturer() != null && toSave.getManufacturer().getId() == null) {
//			toSave.setManufacturer(null);
//		}
//		if (toSave.getHyperdriveSystem() != null && toSave.getHyperdriveSystem().getId() == null) {
//			toSave.setHyperdriveSystem(null);
//		}
//		if (toSave.getWeapons() != null) {
//			if (!toSave.getWeapons().isEmpty()) {
//				toSave.getWeapons().forEach(w -> {
//					if (w.getId() == null) {
//						w = null;
//					}
//				});
//			} else {
//				toSave.setWeapons(null);
//			}
//		}
//		Starship saved = starshipRepository.save(toSave);
//		// Refresh entity to load nested fields
//		// TODO manage case of empty object : {} given as StarshipWeaponInput
//		boolean refresh = false;
//		if (saved.getManufacturer() != null && saved.getManufacturer().getId() != null
//				|| saved.getHyperdriveSystem() != null && saved.getHyperdriveSystem().getId() != null) {
//			refresh = true;
//		}
//		if (!refresh && saved.getWeapons() != null && !saved.getWeapons().isEmpty()) {
//			for (Weapon w : saved.getWeapons()) {
//				if (w.getId() != null) {
//					refresh = true;
//				}
//			}
//		}
//		if (refresh) {
//			entityManager.flush();
//			entityManager.refresh(saved);
//		}
//		return mapper.toStarshipOutput(saved);
//	}
//
//	@Transactional
//	public StarshipOutput updateStarship(final Long id, @Valid final StarshipInput si) {
//		Assert.notNull(id, String.format("id cannot be null."));
//		this.checkStarshipInput(si);
//		// Check Manufacturer
//		if (si.getManufacturerId() != null) {
//			manufacturerService.checkManufacturerExist(si.getManufacturerId());
//		}
//		// Check HyperdriveSystem
//		if (si.getHyperdriveSystemId() != null) {
//			hyperdriveSystemService.checkHyperdriveSystemExist(si.getHyperdriveSystemId());
//		}
//		// Check Weapons
//		if (si.getWeapons() != null && !si.getWeapons().isEmpty()) {
//			si.getWeapons()
//					.forEach(w -> {
//						if (w.getWeaponId() != null) {
//							this.weaponService.checkWeaponExist(w.getWeaponId());
//						}
//					});
//		}
//		Starship toSave = mapper.fromStarshipInput(si);
//		toSave.setId(id);
//		// Avoid hibernate save errors
//		if (toSave.getManufacturer() != null && toSave.getManufacturer().getId() == null) {
//			toSave.setManufacturer(null);
//		}
//		if (toSave.getHyperdriveSystem() != null && toSave.getHyperdriveSystem().getId() == null) {
//			toSave.setHyperdriveSystem(null);
//		}
//		if (toSave.getWeapons() != null) {
//			if (!toSave.getWeapons().isEmpty()) {
//				toSave.getWeapons().forEach(w -> {
//					if (w.getId() == null) {
//						w = null;
//					}
//				});
//			} else {
//				toSave.setWeapons(null);
//			}
//		}
//		Starship saved = starshipRepository.save(toSave);
//		// Refresh entity to load nested fields
//		// TODO manage case of empty object : {} given as StarshipWeaponInput
//		boolean refresh = false;
//		if (saved.getManufacturer() != null && saved.getManufacturer().getId() != null
//				|| saved.getHyperdriveSystem() != null && saved.getHyperdriveSystem().getId() != null) {
//			refresh = true;
//		}
//		if (!refresh && saved.getWeapons() != null && !saved.getWeapons().isEmpty()) {
//			for (Weapon w : saved.getWeapons()) {
//				if (w.getId() != null) {
//					refresh = true;
//				}
//			}
//		}
//		if (refresh) {
//			entityManager.flush();
//			entityManager.refresh(saved);
//		}
//		return mapper.toStarshipOutput(saved);
//	}
//
//	@Transactional
//	public void deleteStarship(final Long id) {
//		Assert.notNull(id, String.format("id cannot be null."));
//		Starship starshipToDelete = this.checkStarshipExist(id);
//		starshipRepository.delete(starshipToDelete);
//	}
//
//	public void checkStarshipInput(StarshipInput si) {
//		Assert.notNull(si, String.format("Starship cannot be null."));
//		Assert.notNull(si.getName(), String.format("Name of Starship cannot be null."));
//		Assert.hasText(si.getName(), String.format("Name of Starship cannot be empty."));
//	}
//
//	public Starship checkStarshipExist(Long id) {
//		return starshipRepository.findById(id)
//				.orElseThrow(
//						() -> new ResourceNotFoundException("Starship doesn't exist with this id: "
//								+ idToHashConverter.convert(id)));
//	}
}