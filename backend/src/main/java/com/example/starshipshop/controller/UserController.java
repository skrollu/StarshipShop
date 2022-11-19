package com.example.starshipshop.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.starshipshop.controller.assembler.UserAssembler;
import com.example.starshipshop.domain.SimpleUserDto;
import com.example.starshipshop.domain.UserRequestInput;
import com.example.starshipshop.exception.ResourceNotFoundException;
import com.example.starshipshop.service.AccountService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/users")
public class UserController {
    
    private final AccountService accountService;
    private final UserAssembler assembler;
    
    @GetMapping("/{pseudo}")
    public ResponseEntity<EntityModel<SimpleUserDto>> getUser(Authentication authentication, @PathVariable String pseudo)
    throws ResourceNotFoundException {
        Assert.notNull(pseudo, "Given pseudo cannot be null");
        SimpleUserDto result = this.accountService.getUserInfo(authentication,
        pseudo);
        if (result == null) {
            throw new ResourceNotFoundException("No user found with the given pseudo");
        }
        return ResponseEntity.ok(EntityModel.of(result, linkTo(methodOn(
        UserController.class).getUser(authentication, pseudo))
        .withSelfRel()));
    }
    
    @PostMapping
    public ResponseEntity<EntityModel<SimpleUserDto>> createUser(Authentication authentication,
    @RequestBody UserRequestInput uri){
        SimpleUserDto result = this.accountService.createUser(authentication, uri);
        EntityModel<SimpleUserDto> response = assembler.toModel(result);
        return ResponseEntity.created(response.getRequiredLink(IanaLinkRelations.SELF)
		.toUri())
		.body(response);
    }
}
