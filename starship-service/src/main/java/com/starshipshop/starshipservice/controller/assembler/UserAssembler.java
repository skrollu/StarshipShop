package com.starshipshop.starshipservice.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.starshipshop.starshipservice.controller.AccountController;
import com.starshipshop.starshipservice.controller.UserController;
import com.starshipshop.starshipservice.domain.user.SimpleUserOutput;
import com.starshipshop.starshipservice.service.mapper.converter.IdToHashConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserAssembler implements RepresentationModelAssembler<SimpleUserOutput, EntityModel<SimpleUserOutput>> {

	private final IdToHashConverter idToHashConverter;

	@Override
	public EntityModel<SimpleUserOutput> toModel(SimpleUserOutput user) {
		return EntityModel.of(
				user,
				linkTo(methodOn(UserController.class).getUser(null, idToHashConverter.convert(user.getId())))
						.withSelfRel(),
				linkTo(methodOn(UserController.class).createUser(null, null))
						.withRel("createUser"),
				linkTo(methodOn(AccountController.class).getMyAccount(null))
						.withRel("myAccount"));
	}
}
