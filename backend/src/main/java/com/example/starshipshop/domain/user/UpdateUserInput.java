package com.example.starshipshop.domain.user;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateUserInput {
    @Nonnull
    @NotBlank
    private String pseudo;
    @Valid
    @Size(max = 3)
    private Set<UpdateUserAddressInput> addresses;
    @Valid
    @Size(max = 3)
    private Set<UpdateUserEmailInput> emails;
    @Valid
    @Size(max = 3)
    private Set<UpdateUserTelephoneInput> telephones;
}
