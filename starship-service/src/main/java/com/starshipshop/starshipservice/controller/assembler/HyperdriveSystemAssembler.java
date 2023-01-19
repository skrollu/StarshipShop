package com.starshipshop.starshipservice.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.starshipshop.starshipservice.controller.HyperdriveSystemController;
import com.starshipshop.starshipservice.domain.hyperdriveSystem.HyperdriveSystemOutput;
import com.starshipshop.starshipservice.service.mapper.converter.IdToHashConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class HyperdriveSystemAssembler
		implements RepresentationModelAssembler<HyperdriveSystemOutput, EntityModel<HyperdriveSystemOutput>> {

	private final IdToHashConverter idToHashConverter;

	@Override
	public EntityModel<HyperdriveSystemOutput> toModel(HyperdriveSystemOutput hyperdriveSystem) {
		return EntityModel.of(hyperdriveSystem,
				linkTo(methodOn(HyperdriveSystemController.class).getHyperdriveSystemById(
						idToHashConverter.convert(hyperdriveSystem.getId()))).withSelfRel(),
				// linkTo(methodOn(HyperdriveSystemController.class).updateHyperdriveSystem(hyperdriveSystem.getId(),
				// HyperdriveSystemMapper.mapToDto(hyperdriveSystem))).withRel("update"),
				linkTo(methodOn(HyperdriveSystemController.class).getHyperdriveSystems()).withRel("hyperdriveSystems"));
	}

	public EntityModel<HyperdriveSystemOutput> toModelWithSelfLink(HyperdriveSystemOutput hyperdriveSystem) {
		return EntityModel.of(hyperdriveSystem,
				linkTo(methodOn(HyperdriveSystemController.class).getHyperdriveSystemById(
						idToHashConverter.convert(hyperdriveSystem.getId()))).withSelfRel());
	}
}