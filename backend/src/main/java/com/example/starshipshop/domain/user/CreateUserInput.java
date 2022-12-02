package com.example.starshipshop.domain.user;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateUserInput {
    @Nonnull
    @NotBlank
    private String pseudo;
    @Size(max = 3)
    private Set<CreateUserAddressInput> addresses;
    @Size(max = 3)
    private Set<CreateUserEmailInput> emails;
    @Size(max = 3)
    private Set<CreateUserTelephoneInput> telephones;
}
