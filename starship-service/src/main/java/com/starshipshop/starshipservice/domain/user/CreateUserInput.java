package com.starshipshop.starshipservice.domain.user;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateUserInput {
    @NotBlank(message = "Pseudo is mandatory and cannot be null, empty or blank")
    private String pseudo;
    @Nullable
    @Size(min = 0, max = 3)
    private Set<CreateUserAddressInput> addresses;
    @Nullable
    @Size(min = 0, max = 3)
    private Set<CreateUserEmailInput> emails;
    @Nullable
    @Size(min = 0, max = 3)
    private Set<CreateUserTelephoneInput> telephones;
}
