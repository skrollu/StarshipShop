package com.example.starshipshop.domain;

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
public class CreateUserInputRequest {
    @Nonnull
    @NotBlank
    private String pseudo;
    @Size(max = 3)
    private Set<CreateUserAddressInputRequest> addresses;
    @Size(max = 3)
    private Set<CreateUserEmailInputRequest> emails;
    @Size(max = 3)
    private Set<CreateUserTelephoneInputRequest> telephones;
}
