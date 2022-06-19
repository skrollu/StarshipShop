package com.example.starshipShop.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.example.starshipShop.dto.HyperdriveSystemDto;
import com.example.starshipShop.dto.ManufacturerDto;
import com.example.starshipShop.dto.StarshipDto;
import com.example.starshipShop.dto.WeaponDto;
import com.example.starshipShop.jpa.HyperdriveSystem;
import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.jpa.Starship;
import com.example.starshipShop.jpa.Weapon;

@Component
@Mapper(componentModel = "spring")
public interface StarshipShopMapper {

	Manufacturer toManufacturer(ManufacturerDto dto);

	ManufacturerDto toManufacturerDto(Manufacturer manufacturer);

	HyperdriveSystem toHyperdriveSystem(HyperdriveSystemDto dto);

	HyperdriveSystemDto toHyperdriveSystemDto(HyperdriveSystem hyperdriveSystem);

	Weapon toWeapon(WeaponDto dto);

	WeaponDto toWeaponDto(Weapon weapon);

	Starship toStarship(StarshipDto dto);

	StarshipDto toStarshipDto(Starship starship);
}
