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
public class UpdateUserRequestInput {
    @Nonnull
    @NotBlank
    private String pseudo;
    @Size(max = 3)
    private Set<AddressDto> addresses;
    @Size(max = 3)
    private Set<EmailDto> emails;
    @Size(max = 3)
    private Set<TelephoneDto> telephones;
}
