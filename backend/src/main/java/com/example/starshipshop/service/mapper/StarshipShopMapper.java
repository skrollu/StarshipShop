package com.example.starshipshop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.starshipshop.domain.hyperdriveSystem.HyperdriveSystemInput;
import com.example.starshipshop.domain.hyperdriveSystem.HyperdriveSystemOutput;
import com.example.starshipshop.domain.manufacturer.ManufacturerInput;
import com.example.starshipshop.domain.manufacturer.ManufacturerOutput;
import com.example.starshipshop.domain.starship.StarshipOutput;
import com.example.starshipshop.domain.starship.StarshipWeaponInput;
import com.example.starshipshop.domain.starship.StarshipInput;
import com.example.starshipshop.domain.weapon.WeaponOutput;
import com.example.starshipshop.domain.weapon.WeaponInput;
import com.example.starshipshop.repository.jpa.HyperdriveSystem;
import com.example.starshipshop.repository.jpa.Manufacturer;
import com.example.starshipshop.repository.jpa.Starship;
import com.example.starshipshop.repository.jpa.Weapon;

@Mapper(componentModel = "spring")
public interface StarshipShopMapper {

	@Mapping(target = "hyperdriveSystems", ignore = true)
	@Mapping(target = "starships", ignore = true)
	@Mapping(target = "weapons", ignore = true)
	Manufacturer toManufacturer(ManufacturerInput input);

	ManufacturerOutput toManufacturerOuput(Manufacturer manufacturer);

	@Mapping(target = "hyperdriveSystems", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "starships", ignore = true)
	@Mapping(target = "weapons", ignore = true)
	Manufacturer fromManufaturerInput(ManufacturerInput mi);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "starships", ignore = true)
	@Mapping(target = "manufacturer.id", source = "manufacturerId")
	HyperdriveSystem toHyperdriveSystem(HyperdriveSystemInput hsi);

	HyperdriveSystemOutput toHyperdriveSystemOutput(HyperdriveSystem hyperdriveSystem);

	WeaponOutput toWeaponOutput(Weapon weapon);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "starships", ignore = true)
	@Mapping(target = "manufacturer.id", source = "manufacturerId")
	Weapon fromWeaponInput(WeaponInput wi);

	StarshipOutput toStarshipOutput(Starship starship);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "manufacturer.id", source = "manufacturerId")
	@Mapping(target = "hyperdriveSystem.id", source = "hyperdriveSystemId")
	Starship fromStarshipInput(StarshipInput si);

	@Mapping(target = "id", source = "weaponId")
	Weapon fromStarshipWeaponInput(StarshipWeaponInput swi);

	StarshipInput toStarshipInput(Starship starship);
}
