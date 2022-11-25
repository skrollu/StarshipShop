package com.example.starshipshop.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import javax.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.starshipshop.common.exception.ResourceNotFoundException;
import com.example.starshipshop.controller.assembler.UserAssembler;
import com.example.starshipshop.domain.AddressDto;
import com.example.starshipshop.domain.AddressRequestInput;
import com.example.starshipshop.domain.CreateUserRequestInput;
import com.example.starshipshop.domain.EmailDto;
import com.example.starshipshop.domain.EmailRequestInput;
import com.example.starshipshop.domain.SimpleUserDto;
import com.example.starshipshop.domain.TelephoneDto;
import com.example.starshipshop.domain.UpdateUserTelephoneRequestInput;
import com.example.starshipshop.domain.UpdateUserRequestInput;
import com.example.starshipshop.service.AccountService;
import com.example.starshipshop.service.mapper.converter.HashToIdConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
        
        private final HashToIdConverter hashToIdConverter;
        private final AccountService accountService;
        private final UserAssembler assembler;
        
        @GetMapping("/{userId}")
        public ResponseEntity<EntityModel<SimpleUserDto>> getUser(Authentication authentication,
        @PathVariable String userId) throws ResourceNotFoundException {
                Assert.notNull(userId, "Given id cannot be null");
                SimpleUserDto result =
                this.accountService.getUserInfo(authentication, hashToIdConverter.convert(
                userId));
                if (result == null) {
                        throw new ResourceNotFoundException("No user found with the given id: " + userId);
                }
                return ResponseEntity.ok(EntityModel.of(result,
                linkTo(methodOn(UserController.class).getUser(authentication, 
                userId)).withSelfRel()));
        }
        
        @PostMapping("/")
        public ResponseEntity<EntityModel<SimpleUserDto>> createUser(Authentication authentication,
        @RequestBody @Valid CreateUserRequestInput curi) {
                SimpleUserDto result = this.accountService.createUser(authentication, curi);
                EntityModel<SimpleUserDto> response = assembler.toModel(result);
                return ResponseEntity.created(response.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(response);
        }
        
        @PutMapping("/{userId}")
        public ResponseEntity<EntityModel<SimpleUserDto>> updateUser(Authentication authentication,
        @RequestBody @Valid UpdateUserRequestInput uuri, @PathVariable String userId) {
                SimpleUserDto result = this.accountService.updateUser(authentication,
                uuri,
                hashToIdConverter.convert(userId));
                EntityModel<SimpleUserDto> response = assembler.toModel(result);
                return ResponseEntity.created(response.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(response);
        }
        
        @PostMapping("/{userId}/emails")
        public EmailDto createEmail(Authentication authentication, @RequestBody @Valid EmailRequestInput eri,
        @PathVariable String userId) {
                EmailDto result = this.accountService.createUserEmail(authentication, eri,
                hashToIdConverter.convert(userId));
                return result;
        }
        
        @PostMapping("/{userId}/telephones")
        public TelephoneDto createTelephone(Authentication authentication, @RequestBody @Valid UpdateUserTelephoneRequestInput eri,
        @PathVariable String userId) {
                TelephoneDto result = this.accountService.createUserTelephone(authentication, eri,
                hashToIdConverter.convert(userId));
                return result;
        }
        
        @PostMapping("/{userId}/addresses")
        public AddressDto createTelephone(Authentication authentication,
        @RequestBody @Valid AddressRequestInput ari, @PathVariable String userId) {
                AddressDto result = this.accountService.createUserAddress(authentication, 
                ari,
                hashToIdConverter.convert(userId));
                return result;
        }
        
        
        
        
}
