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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.starshipshop.common.exception.ResourceNotFoundException;
import com.example.starshipshop.controller.assembler.AccountAssembler;
import com.example.starshipshop.domain.AccountDto;
import com.example.starshipshop.domain.EmailRequestInput;
import com.example.starshipshop.domain.RegisterNewAccountRequestInput;
import com.example.starshipshop.service.AccountService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;
    private final AccountAssembler assembler;

    @GetMapping(value = "/myAccount")
    public ResponseEntity<EntityModel<AccountDto>> getMyAccount(Authentication authentication)
            throws ResourceNotFoundException {
        AccountDto result = this.accountService.getAccountDto(authentication);
        return ResponseEntity.ok(EntityModel.of(result,
                linkTo(methodOn(AccountController.class).getMyAccount(authentication))
                        .withSelfRel()));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<EntityModel<AccountDto>> registerNewAccount(
            @RequestBody @Valid RegisterNewAccountRequestInput rnari) {
        AccountDto result = this.accountService.registerNewAccount(rnari);
        EntityModel<AccountDto> entityModel = this.assembler.toModel(result);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping(value = "/emailExists")
    public ResponseEntity<Map<String, Boolean>> checkIfEmailAccountExists(
            @RequestBody @Valid EmailRequestInput eri) {
        boolean result = this.accountService.checkUsernameExists(eri.getEmail());
        Map<String, Boolean> response = new HashMap<>();
        response.put("exist", Boolean.valueOf(result));
        return ResponseEntity.ok(response);
    }
}
