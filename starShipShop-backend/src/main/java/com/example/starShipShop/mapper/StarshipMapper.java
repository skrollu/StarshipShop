package com.example.starshipShop.mapper;

import java.util.ArrayList;
import java.util.List;

import com.example.starshipShop.jpa.Starship;
import com.example.starshipShop.jpa.Weapon;
import com.example.starshipShop.requestDto.StarshipRequestDTO;
import com.example.starshipShop.requestDto.WeaponRequestDTO;

public class StarshipMapper {

	public static Starship mapToEntity(StarshipRequestDTO starshipRequestDTO) {
		Starship result = new Starship();
		result.setReference(starshipRequestDTO.getReference());
		result.setName(starshipRequestDTO.getName());
		result.setEngines(starshipRequestDTO.getEngines());
		result.setHeight(starshipRequestDTO.getHeight());
		result.setWidth(starshipRequestDTO.getWidth());
		result.setLenght(starshipRequestDTO.getLenght());
		result.setWeight(starshipRequestDTO.getWeight());
		result.setDescription(starshipRequestDTO.getEngines());
		if (starshipRequestDTO.getManufacturer() != null) {
			result.setManufacturer(ManufacturerMapper.mapToEntity(starshipRequestDTO.getManufacturer()));
		}
		if (starshipRequestDTO.getManufacturer() != null) {
			result.setHyperdriveSystem(HyperdriveSystemMapper.mapToEntity(starshipRequestDTO.getHyperdriveSystem()));
		}
		List<Weapon> weapons = new ArrayList<Weapon>();
		for (WeaponRequestDTO w : starshipRequestDTO.getWeapons()) {
			weapons.add(WeaponMapper.mapToEntity(w));
		}
		result.setWeapons(weapons);
		return result;
	}

	public static Starship mapToEntity(StarshipRequestDTO starshipRequestDTO, Starship result) {
		result.setReference(starshipRequestDTO.getReference());
		result.setName(starshipRequestDTO.getName());
		result.setEngines(starshipRequestDTO.getEngines());
		result.setHeight(starshipRequestDTO.getHeight());
		result.setWidth(starshipRequestDTO.getWidth());
		result.setLenght(starshipRequestDTO.getLenght());
		result.setWeight(starshipRequestDTO.getWeight());
		result.setDescription(starshipRequestDTO.getEngines());
		if (starshipRequestDTO.getManufacturer() != null) {
			result.setManufacturer(ManufacturerMapper.mapToEntity(starshipRequestDTO.getManufacturer()));
		}
		if (starshipRequestDTO.getManufacturer() != null) {
			result.setHyperdriveSystem(HyperdriveSystemMapper.mapToEntity(starshipRequestDTO.getHyperdriveSystem()));
		}
		if (!starshipRequestDTO	.getWeapons()
								.isEmpty()) {
			List<Weapon> weapons = new ArrayList<Weapon>();
			for (WeaponRequestDTO w : starshipRequestDTO.getWeapons()) {
				weapons.add(WeaponMapper.mapToEntity(w));
			}
			result.setWeapons(weapons);
		}
		return result;
	}
	/*
	 * public static StarshipRequestDTO mapToDto(Starship starship) {
	 * StarshipRequestDTO result = new StarshipRequestDTO();
	 * result.setReference(starship.getReference());
	 * result.setName(starship.getName()); result.setEngines(starship.getEngines());
	 * result.setHeight(starship.getHeight()); result.setWidth(starship.getWidth());
	 * result.setLenght(starship.getLenght());
	 * result.setWeight(starship.getWeight());
	 * result.setDescription(starship.getEngines());
	 * result.setManufacturer(starship.getManufacturer());
	 * result.setHyperdriveSystem(starship.getHyperdriveSystem());
	 * result.setWeapons(starship.getWeapons()); return result; }
	 */
}
