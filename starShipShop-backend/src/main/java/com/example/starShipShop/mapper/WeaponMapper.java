package com.example.starshipShop.mapper;

import com.example.starshipShop.jpa.Weapon;
import com.example.starshipShop.requestDto.WeaponRequestDTO;

public class WeaponMapper {
	public static Weapon mapToEntity(WeaponRequestDTO weaponRequestDTO) {
		Weapon result = new Weapon();
		result.setReference(weaponRequestDTO.getReference());
		result.setName(weaponRequestDTO.getName());
		if (weaponRequestDTO.getManufacturer() != null) {
			result.setManufacturer(ManufacturerMapper.mapToEntity(weaponRequestDTO.getManufacturer()));
		}
		return result;
	}

	public static Weapon mapToEntity(WeaponRequestDTO weaponRequestDTO, Weapon result) {
		result.setReference(weaponRequestDTO.getReference());
		result.setName(weaponRequestDTO.getName());
		if (weaponRequestDTO.getManufacturer() != null) {
			result.setManufacturer(ManufacturerMapper.mapToEntity(weaponRequestDTO.getManufacturer()));
		}
		return result;
	}
	/*
	 * public static WeaponRequestDTO mapToDto(Weapon weapon) { WeaponRequestDTO
	 * result = new WeaponRequestDTO(); result.setReference(weapon.getReference());
	 * result.setName(weapon.getName()); result.setEngines(weapon.getEngines());
	 * result.setReference(weaponRequestDTO.getReference());
	 * result.setManufacturer(weapon.getManufacturer());
	 */

}
