package com.example.starshipshop.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.starshipshop.domain.AccountDto;
import com.example.starshipshop.domain.EmailRequestInput;
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
    public ResponseEntity<EntityModel<AccountDto>> getMyAccount(Authentication authentication) throws ResourceNotFoundException {
        Optional<AccountDto> result = this.accountService.getAccount(authentication);
        if(result.isPresent()) {
            return ResponseEntity.ok(EntityModel.of(result.get(),
            linkTo(methodOn(AccountController.class).getMyAccount(authentication))
            .withSelfRel()));
        }
        else {
            throw new ResourceNotFoundException("Account information not found");
        }
    }
    
    @PostMapping(value = "/register")
    public ResponseEntity<AccountDto> registerNewAccount(@RequestBody @Valid RegisterNewAccountRequestInput rnari) {
        return  ResponseEntity.ok(this.accountService.registerNewAccount(rnari));
    }
    
    @PostMapping(value = "/emailExists")
    public ResponseEntity<Map<String, Boolean>> checkIfEmailAccountExists(@RequestBody @Valid EmailRequestInput eri) {
        boolean result = this.accountService.checkEmailExists(eri.getEmail());
        Map<String, Boolean> response = new HashMap<>();
        response.put("exist", Boolean.valueOf(result));
        return ResponseEntity.ok(response);
    }
}