package com.starshipshop.starshipservice.domain.user;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateUserInput {
    @NotBlank(message = "Pseudo is mandatory and cannot be null, empty or blank")
    private String pseudo;
    @Valid
    @Nullable
    @Size(min = 0, max = 3)
    private Set<UpdateUserAddressInput> addresses;
    @Valid
    @Nullable
    @Size(min = 0, max = 3)
    private Set<UpdateUserEmailInput> emails;
    @Valid
    @Nullable
    @Size(min = 0, max = 3)
    private Set<UpdateUserTelephoneInput> telephones;
}
