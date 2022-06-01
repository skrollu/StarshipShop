package com.example.starshipShop.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.starshipShop.controller.StarshipController;
import com.example.starshipShop.jpa.Starship;

@Component
public class StarshipAssembler implements RepresentationModelAssembler<Starship, EntityModel<Starship>> {

	@Override
	public EntityModel<Starship> toModel(Starship starship) {
		return EntityModel.of(starship,
				linkTo(methodOn(StarshipController.class).getStarshipById(starship.getId())).withSelfRel(),
				linkTo(methodOn(StarshipController.class).getStarships()).withRel("starships"));
	}

	public EntityModel<Starship> toModelWithSelfLink(Starship starship) {
		return EntityModel.of(starship,
				linkTo(methodOn(StarshipController.class).getStarshipById(starship.getId())).withSelfRel());

	}
}