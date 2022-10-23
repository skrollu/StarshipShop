package com.example.starshipShop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.starshipShop.domain.HyperdriveSystemDto;
import com.example.starshipShop.domain.HyperdriveSystemRequestInput;
import com.example.starshipShop.domain.ManufacturerDto;
import com.example.starshipShop.domain.ManufacturerRequestInput;
import com.example.starshipShop.domain.StarshipDto;
import com.example.starshipShop.domain.StarshipRequestInput;
import com.example.starshipShop.domain.WeaponDto;
import com.example.starshipShop.domain.WeaponRequestInput;
import com.example.starshipShop.repository.jpa.HyperdriveSystem;
import com.example.starshipShop.repository.jpa.Manufacturer;
import com.example.starshipShop.repository.jpa.Starship;
import com.example.starshipShop.repository.jpa.Weapon;

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

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "starships", ignore = true)
	Weapon fromWeaponRequestInput(WeaponRequestInput hsri);

	WeaponRequestInput toWeaponRequestInput(Weapon weapon);
	
	Starship toStarship(StarshipDto dto);

	StarshipDto toStarshipDto(Starship starship);

	@Mapping(target = "id", ignore = true)
	Starship fromStarshipRequestInput(StarshipRequestInput sri);

	StarshipRequestInput toStarshipRequestInput(Starship starship);
}
