package com.example.starshipshop.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipshop.common.exception.ResourceNotFoundException;
import com.example.starshipshop.controller.assembler.AccountAssembler;
import com.example.starshipshop.domain.account.AccountOutput;
import com.example.starshipshop.domain.account.CreateAccountInput;
import com.example.starshipshop.domain.user.CreateUserEmailInput;
import com.example.starshipshop.service.AccountService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;
    private final AccountAssembler assembler;

    @GetMapping(value = "/myAccount")
    public ResponseEntity<EntityModel<AccountOutput>> getMyAccount(Authentication authentication)
            throws ResourceNotFoundException {
        AccountOutput result = this.accountService.getAccountOutput(authentication);
        return ResponseEntity.ok(EntityModel.of(result,
                linkTo(methodOn(AccountController.class).getMyAccount(authentication))
                        .withSelfRel()));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<EntityModel<AccountOutput>> registerNewAccount(
            @RequestBody @Valid CreateAccountInput cai) {
        AccountOutput result = this.accountService.createAccount(cai);
        EntityModel<AccountOutput> entityModel = this.assembler.toModel(result);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<EntityModel<AccountOutput>> registerNewAccount(
            @RequestBody @Valid CreateAccountInput cai, Authentication authentication) {
        AccountOutput result = this.accountService.updateAccount(cai, authentication);
        EntityModel<AccountOutput> entityModel = this.assembler.toModel(result);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping(value = "/emailExists")
    public ResponseEntity<Map<String, Boolean>> checkIfEmailAccountExists(
            @RequestBody @Valid CreateUserEmailInput cuei) {
        boolean result = this.accountService.checkUsernameExists(cuei.getEmail());
        Map<String, Boolean> response = new HashMap<>();
        response.put("exist", Boolean.valueOf(result));
        return ResponseEntity.ok(response);
    }
}
