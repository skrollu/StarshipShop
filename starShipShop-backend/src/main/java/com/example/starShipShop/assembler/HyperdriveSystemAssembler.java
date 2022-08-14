package com.example.starshipShop.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.starshipShop.controller.HyperdriveSystemController;
import com.example.starshipShop.dto.HyperdriveSystemDto;
import com.example.starshipShop.mapper.converter.IdToHashConverter;

@Component
public class HyperdriveSystemAssembler
		implements RepresentationModelAssembler<HyperdriveSystemDto, EntityModel<HyperdriveSystemDto>> {

	@Override
	public EntityModel<HyperdriveSystemDto> toModel(HyperdriveSystemDto hyperdriveSystem) {
		return EntityModel.of(hyperdriveSystem,
				linkTo(methodOn(HyperdriveSystemController.class).getHyperdriveSystemById(
						IdToHashConverter.convertToHash(hyperdriveSystem.getId()))).withSelfRel(),
				// linkTo(methodOn(HyperdriveSystemController.class).updateHyperdriveSystem(hyperdriveSystem.getId(),
				// HyperdriveSystemMapper.mapToDto(hyperdriveSystem))).withRel("update"),
				linkTo(methodOn(HyperdriveSystemController.class).getHyperdriveSystems()).withRel("hyperdriveSystems"));
	}

	public EntityModel<HyperdriveSystemDto> toModelWithSelfLink(HyperdriveSystemDto hyperdriveSystem) {
		return EntityModel.of(hyperdriveSystem,
				linkTo(methodOn(HyperdriveSystemController.class).getHyperdriveSystemById(
						IdToHashConverter.convertToHash(hyperdriveSystem.getId()))).withSelfRel());
	}
}