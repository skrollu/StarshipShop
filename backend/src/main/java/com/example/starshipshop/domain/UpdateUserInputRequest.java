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
public class UpdateUserInputRequest {
    @Nonnull
    @NotBlank
    private String pseudo;
    @Size(max = 3)
    private Set<UpdateUserAddressRequestInput> addresses;
    @Size(max = 3)
    private Set<UpdateUserEmailRequestInput> emails;
    @Size(max = 3)
    private Set<UpdateUserTelephoneRequestInput> telephones;
}
