package com.starshipshop.starshipservice.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.starshipshop.starshipservice.controller.WeaponController;
import com.starshipshop.starshipservice.domain.weapon.WeaponOutput;
import com.starshipshop.starshipservice.service.mapper.converter.IdToHashConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class WeaponAssembler implements RepresentationModelAssembler<WeaponOutput, EntityModel<WeaponOutput>> {

	private final IdToHashConverter idToHashConverter;

	@Override
	public EntityModel<WeaponOutput> toModel(WeaponOutput weapon) {
		return EntityModel.of(weapon,
				linkTo(methodOn(WeaponController.class).getWeaponById(
						idToHashConverter.convert(weapon.getId()))).withSelfRel(),
				// linkTo(methodOn(WeaponController.class).updateHyperdriveSystem(hyperdriveSystem.getId(),
				// HyperdriveSystemMapper.mapToDto(hyperdriveSystem))).withRel("update"),
				linkTo(methodOn(WeaponController.class).getWeapons()).withRel("weapons"));
	}

	public EntityModel<WeaponOutput> toModelWithSelfLink(WeaponOutput weapon) {
		return EntityModel.of(weapon, linkTo(methodOn(WeaponController.class).getWeaponById(
				idToHashConverter.convert(weapon.getId()))).withSelfRel());
	}
}