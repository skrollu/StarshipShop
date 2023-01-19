package com.starshipshop.starshipservice.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.starshipshop.starshipservice.controller.AccountController;
import com.starshipshop.starshipservice.domain.account.AccountOutput;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AccountAssembler implements RepresentationModelAssembler<AccountOutput, EntityModel<AccountOutput>> {

    @Override
    public EntityModel<AccountOutput> toModel(AccountOutput accountDto) {
        return EntityModel.of(
                accountDto,
                linkTo(methodOn(AccountController.class).getMyAccount(null))
                        .withSelfRel());
    }
}
