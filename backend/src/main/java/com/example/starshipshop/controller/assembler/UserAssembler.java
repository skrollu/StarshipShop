package com.example.starshipshop.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.example.starshipshop.controller.AccountController;
import com.example.starshipshop.controller.UserController;
import com.example.starshipshop.domain.SimpleUserDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserAssembler implements RepresentationModelAssembler<SimpleUserDto, EntityModel<SimpleUserDto>> {
    
    @Override
    public EntityModel<SimpleUserDto> toModel(SimpleUserDto user) {
        return EntityModel.of(
        user, 
        linkTo(methodOn(UserController.class).getUser(null, user.getPseudo()))
            .withSelfRel(),
        linkTo(methodOn(UserController.class).createUser(null, null))
            .withRel("createUser"),
        linkTo(methodOn(AccountController.class).getMyAccount(null))
            .withRel("myAccount"));
    }    
}
