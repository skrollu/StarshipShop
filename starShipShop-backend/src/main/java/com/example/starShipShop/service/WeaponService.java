package com.example.starshipShop.service;

import static com.example.starshipShop.mapper.WeaponMapper.mapToEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.starshipShop.exception.ResourceNotFoundException;
import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.jpa.Weapon;
import com.example.starshipShop.repository.WeaponRepository;
import com.example.starshipShop.requestDto.WeaponRequestDTO;

import lombok.Data;

@Data
@Service
public class WeaponService {

	@Autowired
	private WeaponRepository weaponRepository;

	@Autowired
	private ManufacturerService manufacturerService;

	public List<Weapon> getWeapons() {
		List<Weapon> list = weaponRepository.findAll();
		return list;
	}

	public Optional<Weapon> getWeaponById(final Long id) {
		return weaponRepository.findById(id);
	}

	public Optional<Weapon> getWeaponByName(final String name) {
		return weaponRepository.findByName(name);
	}

	public Weapon saveWeapon(Weapon weapon) {
		Weapon savedWeapon = weaponRepository.save(weapon);
		return savedWeapon;
	}

	public Weapon saveWeapon(final WeaponRequestDTO weaponRequestDTO) {
		// Check nullpointer of Manufacturer
		if (weaponRequestDTO.getManufacturer() == null) {
			throw new NullPointerException("The given Manufacturer is null");
		}
		if (weaponRequestDTO.getManufacturer()
							.getName() == null
				|| weaponRequestDTO	.getManufacturer()
									.getName()
									.isEmpty()) {
			throw new NullPointerException("The given Manufacturer's name is null or empty");
		}

		Weapon weapon = mapToEntity(weaponRequestDTO);

		Manufacturer manufacturer = this.manufacturerService.getManufacturerByName(weapon	.getManufacturer()
																							.getName())
															.orElseThrow(() -> new ResourceNotFoundException(
																	"The given Manufacturer doesn't exist with this name: "
																			+ weapon.getManufacturer()
																					.getName()));
		weapon.setManufacturer(manufacturer);
		return weaponRepository.save(weapon);
	}

	public Weapon updateWeapon(final Long id, WeaponRequestDTO weaponRequestDTO) {
		// Check nullpointer of Manufacturer
		if (weaponRequestDTO.getManufacturer() == null) {
			throw new NullPointerException("The given Manufacturer is null");
		}
		if (weaponRequestDTO.getManufacturer()
							.getName() == null
				|| weaponRequestDTO	.getManufacturer()
									.getName()
									.isEmpty()) {
			throw new NullPointerException("The given Manufacturer's name is null or empty");
		}

		Weapon weaponFromDb = this	.getWeaponById(id)
									.orElseThrow(() -> new ResourceNotFoundException(
											"Weapon doesn't exist with this id " + id));
		Weapon weaponToUpdate = mapToEntity(weaponRequestDTO, weaponFromDb);

		Manufacturer manufacturer = this.manufacturerService.getManufacturerByName(weaponRequestDTO	.getManufacturer()
																									.getName())
															.orElseThrow(() -> new ResourceNotFoundException(
																	"The given Manufacturer doesn't exist with this name: "
																			+ weaponRequestDTO	.getManufacturer()
																								.getName()));
		weaponToUpdate.setManufacturer(manufacturer);
		return this.saveWeapon(weaponToUpdate);
	}

	public void deleteWeapon(final Long id) {
		Weapon weaponToDelete = this.getWeaponById(id)
									.orElseThrow(() -> new ResourceNotFoundException(
											"Weapon doesn't exist with this id " + id));
		weaponRepository.delete(weaponToDelete);
	}
}
