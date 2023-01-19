package com.starshipshop.starshipservice.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.starshipshop.starshipservice.controller.StarshipController;
import com.starshipshop.starshipservice.domain.starship.StarshipOutput;
import com.starshipshop.starshipservice.service.mapper.converter.IdToHashConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StarshipAssembler implements RepresentationModelAssembler<StarshipOutput, EntityModel<StarshipOutput>> {

	private final IdToHashConverter idToHashConverter;

	@Override
	public EntityModel<StarshipOutput> toModel(StarshipOutput starship) {
		return EntityModel.of(starship,
				linkTo(methodOn(StarshipController.class).getStarshipById(idToHashConverter.convert(starship.getId())))
						.withSelfRel(),
				linkTo(methodOn(StarshipController.class).getStarships()).withRel("starships"));
	}

	public EntityModel<StarshipOutput> toModelWithSelfLink(StarshipOutput starship) {
		return EntityModel.of(starship, linkTo(methodOn(StarshipController.class).getStarshipById(
				idToHashConverter.convert(starship.getId()))).withSelfRel());
	}
}