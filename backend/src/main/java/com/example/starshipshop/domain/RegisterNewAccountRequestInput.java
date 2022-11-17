package com.example.starshipshop.domain;

import javax.annotation.Nonnull;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterNewAccountRequestInput {
	@Email
	@Nonnull
	private String username;
	
	@Size(min = 8)
	@Nonnull
	private String password;
	
	@Size(min = 8)
	@Nonnull
    private String matchingPassword;
}
