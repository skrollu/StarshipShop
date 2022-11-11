package com.example.starshipshop.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.starshipshop.domain.AccountDto;
import com.example.starshipshop.domain.RegisterNewAccountRequestInput;
import com.example.starshipshop.exception.ResourceNotFoundException;
import com.example.starshipshop.service.AccountService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/account")
public class AccountController {
    
    private final AccountService accountService;
    
    @GetMapping(value = "/myAccount")
    @ResponseBody
    public EntityModel<AccountDto> getMyAccount(Authentication authentication) throws ResourceNotFoundException {
        Optional<AccountDto> result = this.accountService.getAccount(authentication);
        if(result.isPresent()) {
            return EntityModel.of(result.get(),
            linkTo(methodOn(AccountController.class).getMyAccount(authentication))
            .withSelfRel());
        }
        else {
            throw new ResourceNotFoundException("Account information not found");
        }
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public AccountDto registerNewAccount(@RequestBody RegisterNewAccountRequestInput rnari) {
        return this.accountService.registerNewAccount(rnari);
    }
}

