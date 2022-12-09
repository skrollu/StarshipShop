package com.example.starshipshop.domain.account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateAccountInput {
	@NotBlank(message = "Username is mandatory and cannot be null, empty or blank")
	@Email(message = "Username must respect a valid email")
	private String username;

	@Size(min = 8)
	@NotBlank(message = "Password is mandatory and cannot be null, empty or blank")
	private String password;

	@Size(min = 8)
	@NotBlank(message = "Matching password is mandatory and cannot be null, empty or blank")
	private String matchingPassword;
}
