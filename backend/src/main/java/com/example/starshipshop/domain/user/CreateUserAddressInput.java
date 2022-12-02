package com.example.starshipshop.domain.user;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserAddressInput {
    @NonNull
    @NotBlank
    private String address;
    private Long zipCode;
    @NonNull
    @NotBlank
    private String city;
    @NonNull
    @NotBlank
    private String state;
    @NonNull
    @NotBlank
    private String country;
    @NonNull
    @NotBlank
    private String planet;
}
