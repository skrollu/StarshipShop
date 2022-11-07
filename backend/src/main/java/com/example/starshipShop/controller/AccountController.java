package com.example.starshipShop.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.Optional;
import org.springframework.hateoas.EntityModel;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.starshipShop.domain.AccountDto;
import com.example.starshipShop.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping(value = "/myAccount")
    @ResponseBody
    public EntityModel<AccountDto> getMyAccount(@NonNull Authentication authentication) {
        Optional<AccountDto> result = this.accountService.getMyAccount(authentication);
        // TODO error
        return EntityModel.of(result.get(),
                linkTo(methodOn(AccountController.class).getMyAccount(authentication))
                        .withSelfRel());
    }
}

