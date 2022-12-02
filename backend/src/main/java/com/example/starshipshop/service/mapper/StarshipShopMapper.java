package com.example.starshipshop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.starshipshop.domain.HyperdriveSystemDto;
import com.example.starshipshop.domain.HyperdriveSystemInput;
import com.example.starshipshop.domain.ManufacturerDto;
import com.example.starshipshop.domain.ManufacturerInput;
import com.example.starshipshop.domain.StarshipDto;
import com.example.starshipshop.domain.StarshipInput;
import com.example.starshipshop.domain.WeaponDto;
import com.example.starshipshop.domain.WeaponInput;
import com.example.starshipshop.repository.jpa.HyperdriveSystem;
import com.example.starshipshop.repository.jpa.Manufacturer;
import com.example.starshipshop.repository.jpa.Starship;
import com.example.starshipshop.repository.jpa.Weapon;

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
	Manufacturer fromManufaturerRequestInput(ManufacturerInput mi);

	ManufacturerInput toManufaturerRequestInput(Manufacturer manufacturer);

	@Mapping(target = "starships", ignore = true)
	HyperdriveSystem toHyperdriveSystem(HyperdriveSystemDto dto);

	HyperdriveSystemDto toHyperdriveSystemDto(HyperdriveSystem hyperdriveSystem);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "starships", ignore = true)
	HyperdriveSystem fromHyperdriveSystemRequestInput(HyperdriveSystemInput hsri);

	HyperdriveSystemInput toHyperdriveSystemRequestInput(HyperdriveSystem hyperdriveSystem);

	@Mapping(target = "starships", ignore = true)
	Weapon toWeapon(WeaponDto dto);

	WeaponDto toWeaponDto(Weapon weapon);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "starships", ignore = true)
	Weapon fromWeaponRequestInput(WeaponInput wi);

	WeaponInput toWeaponRequestInput(Weapon weapon);
	
	Starship toStarship(StarshipDto dto);

	StarshipDto toStarshipDto(Starship starship);

	@Mapping(target = "id", ignore = true)
	Starship fromStarshipRequestInput(StarshipInput si);

	StarshipInput toStarshipRequestInput(Starship starship);
}
