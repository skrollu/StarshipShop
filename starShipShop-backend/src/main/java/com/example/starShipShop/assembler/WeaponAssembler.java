package com.example.starshipShop.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.starshipShop.controller.WeaponController;
import com.example.starshipShop.jpa.Weapon;

@Component
public class WeaponAssembler implements RepresentationModelAssembler<Weapon, EntityModel<Weapon>> {

	@Override
	public EntityModel<Weapon> toModel(Weapon weapon) {
		return EntityModel.of(weapon,
				linkTo(methodOn(WeaponController.class).getWeaponById(weapon.getId())).withSelfRel(),
				linkTo(methodOn(WeaponController.class).getWeapons()).withRel("weapons"));
	}

	public EntityModel<Weapon> toModelWithSelfLink(Weapon weapon) {
		return EntityModel.of(weapon,
				linkTo(methodOn(WeaponController.class).getWeaponById(weapon.getId())).withSelfRel());

	}
}