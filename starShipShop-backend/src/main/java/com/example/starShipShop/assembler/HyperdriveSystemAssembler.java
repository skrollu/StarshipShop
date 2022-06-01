package com.example.starshipShop.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.starshipShop.controller.HyperdriveSystemController;
import com.example.starshipShop.jpa.HyperdriveSystem;

@Component
public class HyperdriveSystemAssembler
		implements RepresentationModelAssembler<HyperdriveSystem, EntityModel<HyperdriveSystem>> {

	@Override
	public EntityModel<HyperdriveSystem> toModel(HyperdriveSystem hyperdriveSystem) {
		return EntityModel.of(hyperdriveSystem,
				linkTo(methodOn(HyperdriveSystemController.class).getHyperdriveSystemById(
						hyperdriveSystem.getId())).withSelfRel(),
				// linkTo(methodOn(HyperdriveSystemController.class).updateHyperdriveSystem(hyperdriveSystem.getId(),
				// HyperdriveSystemMapper.mapToDto(hyperdriveSystem))).withRel("update"),
				linkTo(methodOn(HyperdriveSystemController.class).getHyperdriveSystems()).withRel("hyperdriveSystems"));
	}

	public EntityModel<HyperdriveSystem> toModelWithSelfLink(HyperdriveSystem hyperdriveSystem) {
		return EntityModel.of(hyperdriveSystem,
				linkTo(methodOn(HyperdriveSystemController.class).getHyperdriveSystemById(
						hyperdriveSystem.getId())).withSelfRel());

	}
}