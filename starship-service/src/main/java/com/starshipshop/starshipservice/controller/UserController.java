package com.starshipshop.starshipservice.controller;

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

import com.starshipshop.starshipservice.common.exception.ResourceNotFoundException;
import com.starshipshop.starshipservice.controller.assembler.UserAssembler;
import com.starshipshop.starshipservice.domain.user.AddressOutput;
import com.starshipshop.starshipservice.domain.user.CreateUserAddressInput;
import com.starshipshop.starshipservice.domain.user.CreateUserEmailInput;
import com.starshipshop.starshipservice.domain.user.CreateUserInput;
import com.starshipshop.starshipservice.domain.user.CreateUserTelephoneInput;
import com.starshipshop.starshipservice.domain.user.EmailOutput;
import com.starshipshop.starshipservice.domain.user.SimpleUserOutput;
import com.starshipshop.starshipservice.domain.user.TelephoneOutput;
import com.starshipshop.starshipservice.domain.user.UpdateUserInput;
import com.starshipshop.starshipservice.service.AccountService;
import com.starshipshop.starshipservice.service.mapper.converter.HashToIdConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private final HashToIdConverter hashToIdConverter;
	private final AccountService accountService;
	private final UserAssembler assembler;

	@GetMapping("/{userId}")
	public ResponseEntity<EntityModel<SimpleUserOutput>> getUser(Authentication authentication,
			@PathVariable String userId) throws ResourceNotFoundException {
		Assert.notNull(userId, "Given id cannot be null");
		SimpleUserOutput result = this.accountService.getUserInfo(authentication, hashToIdConverter.convert(
				userId));
		return ResponseEntity.ok(EntityModel.of(result,
				linkTo(methodOn(UserController.class).getUser(authentication,
						userId)).withSelfRel()));
	}

	@PostMapping("/")
	public ResponseEntity<EntityModel<SimpleUserOutput>> createUser(Authentication authentication,
			@RequestBody @Valid CreateUserInput cui) {
		SimpleUserOutput result = this.accountService.createUser(authentication, cui);
		EntityModel<SimpleUserOutput> response = assembler.toModel(result);
		return ResponseEntity.created(response.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(response);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<EntityModel<SimpleUserOutput>> updateUser(Authentication authentication,
			@RequestBody @Valid UpdateUserInput uui, @PathVariable String userId) {
		SimpleUserOutput result = this.accountService.updateUser(authentication, uui,
				hashToIdConverter.convert(userId));
		EntityModel<SimpleUserOutput> response = assembler.toModel(result);
		return ResponseEntity.created(response.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(response);
	}

	@PostMapping("/{userId}/emails")
	public EmailOutput createEmail(Authentication authentication, @RequestBody @Valid CreateUserEmailInput cuei,
			@PathVariable String userId) {
		return this.accountService.createUserEmail(authentication, cuei,
				hashToIdConverter.convert(userId));
	}

	@PostMapping("/{userId}/telephones")
	public TelephoneOutput createTelephone(Authentication authentication,
			@RequestBody @Valid CreateUserTelephoneInput cuti,
			@PathVariable String userId) {
		return this.accountService.createUserTelephone(authentication, cuti,
				hashToIdConverter.convert(userId));
	}

	@PostMapping("/{userId}/addresses")
	public AddressOutput createTelephone(Authentication authentication,
			@RequestBody @Valid CreateUserAddressInput cuai, @PathVariable String userId) {
		return this.accountService.createUserAddress(authentication, cuai, hashToIdConverter.convert(userId));
	}
}