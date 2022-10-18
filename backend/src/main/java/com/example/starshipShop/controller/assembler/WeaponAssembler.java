package com.example.starshipShop.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.example.starshipShop.controller.WeaponController;
import com.example.starshipShop.dto.WeaponDto;
import com.example.starshipShop.service.mapper.converter.IdToHashConverter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class WeaponAssembler implements RepresentationModelAssembler<WeaponDto, EntityModel<WeaponDto>> {

	private final IdToHashConverter idToHashConverter;
	
	@Override
	public EntityModel<WeaponDto> toModel(WeaponDto weapon) {
		return EntityModel.of(weapon,
				linkTo(methodOn(WeaponController.class).getWeaponById(
						idToHashConverter.convert(weapon.getId()))).withSelfRel(),
				// linkTo(methodOn(WeaponController.class).updateHyperdriveSystem(hyperdriveSystem.getId(),
				// HyperdriveSystemMapper.mapToDto(hyperdriveSystem))).withRel("update"),
				linkTo(methodOn(WeaponController.class).getWeapons()).withRel("weapons"));
	}

	public EntityModel<WeaponDto> toModelWithSelfLink(WeaponDto weapon) {
		return EntityModel.of(weapon, linkTo(methodOn(WeaponController.class).getWeaponById(
				idToHashConverter.convert(weapon.getId()))).withSelfRel());
	}
}