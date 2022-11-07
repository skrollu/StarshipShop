package com.example.starshipshop.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.example.starshipshop.controller.StarshipController;
import com.example.starshipshop.domain.StarshipDto;
import com.example.starshipshop.service.mapper.converter.IdToHashConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StarshipAssembler implements RepresentationModelAssembler<StarshipDto, EntityModel<StarshipDto>> {

	private final IdToHashConverter idToHashConverter;
	
	@Override
	public EntityModel<StarshipDto> toModel(StarshipDto starship) {
		return EntityModel.of(starship,
				linkTo(methodOn(StarshipController.class).getStarshipById(idToHashConverter.convert(starship.getId()))).withSelfRel(),
				linkTo(methodOn(StarshipController.class).getStarships()).withRel("starships"));
	}

	public EntityModel<StarshipDto> toModelWithSelfLink(StarshipDto starship) {
		return EntityModel.of(starship,	linkTo(methodOn(StarshipController.class).getStarshipById(
            idToHashConverter.convert(starship.getId()))).withSelfRel());
	}
}