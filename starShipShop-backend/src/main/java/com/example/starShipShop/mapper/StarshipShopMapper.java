package com.example.starshipShop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.starshipShop.dto.HyperdriveSystemDto;
import com.example.starshipShop.dto.HyperdriveSystemRequestInput;
import com.example.starshipShop.dto.ManufacturerDto;
import com.example.starshipShop.dto.ManufacturerRequestInput;
import com.example.starshipShop.dto.StarshipDto;
import com.example.starshipShop.dto.WeaponDto;
import com.example.starshipShop.dto.WeaponRequestInput;
import com.example.starshipShop.jpa.HyperdriveSystem;
import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.jpa.Starship;
import com.example.starshipShop.jpa.Weapon;

@Mapper(componentModel = "spring")
public interface StarshipShopMapper {

	@Mapping(target = "hyperdriveSystems", ignore = true)
	@Mapping(target = "starships", ignore = true)
	@Mapping(target = "weapons", ignore = true)
	Manufacturer toManufacturer(ManufacturerDto dto);

	ManufacturerDto toManufacturerDto(Manufacturer manufacturer);

	@Mapping(target = "hyperdriveSystems", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "starships", ignore = true)
	@Mapping(target = "weapons", ignore = true)
	Manufacturer fromManufaturerRequestInput(ManufacturerRequestInput mri);

	ManufacturerRequestInput toManufaturerRequestInput(Manufacturer manufacturer);

	@Mapping(target = "starships", ignore = true)
	HyperdriveSystem toHyperdriveSystem(HyperdriveSystemDto dto);

	HyperdriveSystemDto toHyperdriveSystemDto(HyperdriveSystem hyperdriveSystem);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "starships", ignore = true)
	HyperdriveSystem fromHyperdriveSystemRequestInput(HyperdriveSystemRequestInput hsri);

	HyperdriveSystemRequestInput toHyperdriveSystemRequestInput(HyperdriveSystem hyperdriveSystem);

	@Mapping(target = "starships", ignore = true)
	Weapon toWeapon(WeaponDto dto);

	WeaponDto toWeaponDto(Weapon weapon);

	Weapon fromWeaponRequestInput(WeaponRequestInput hsri);

	WeaponRequestInput toWeaponRequestInput(Weapon weapon);
	
	Starship toStarship(StarshipDto dto);

	StarshipDto toStarshipDto(Starship starship);
}
