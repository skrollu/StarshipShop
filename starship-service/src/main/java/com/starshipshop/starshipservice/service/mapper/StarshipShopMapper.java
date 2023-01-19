package com.starshipshop.starshipservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.starshipshop.starshipservice.domain.hyperdriveSystem.HyperdriveSystemInput;
import com.starshipshop.starshipservice.domain.hyperdriveSystem.HyperdriveSystemOutput;
import com.starshipshop.starshipservice.domain.manufacturer.ManufacturerInput;
import com.starshipshop.starshipservice.domain.manufacturer.ManufacturerOutput;
import com.starshipshop.starshipservice.domain.starship.StarshipInput;
import com.starshipshop.starshipservice.domain.starship.StarshipOutput;
import com.starshipshop.starshipservice.domain.starship.StarshipWeaponInput;
import com.starshipshop.starshipservice.domain.weapon.WeaponInput;
import com.starshipshop.starshipservice.domain.weapon.WeaponOutput;
import com.starshipshop.starshipservice.repository.jpa.HyperdriveSystem;
import com.starshipshop.starshipservice.repository.jpa.Manufacturer;
import com.starshipshop.starshipservice.repository.jpa.Starship;
import com.starshipshop.starshipservice.repository.jpa.Weapon;

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
