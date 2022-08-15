package com.example.starshipShop.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.starshipShop.controller.StarshipController;
import com.example.starshipShop.dto.StarshipDto;
import com.example.starshipShop.mapper.converter.IdToHashConverter;

@Component
public class StarshipAssembler implements RepresentationModelAssembler<StarshipDto, EntityModel<StarshipDto>> {

	@Override
	public EntityModel<StarshipDto> toModel(StarshipDto starship) {
		return EntityModel.of(starship,
				linkTo(methodOn(StarshipController.class).getStarshipById(IdToHashConverter.convertToHash(starship.getId()))).withSelfRel(),
				linkTo(methodOn(StarshipController.class).getStarships()).withRel("starships"));
	}

	public EntityModel<StarshipDto> toModelWithSelfLink(StarshipDto starship) {
		return EntityModel.of(starship,	linkTo(methodOn(StarshipController.class).getStarshipById(
            IdToHashConverter.convertToHash(starship.getId()))).withSelfRel());
	}
}