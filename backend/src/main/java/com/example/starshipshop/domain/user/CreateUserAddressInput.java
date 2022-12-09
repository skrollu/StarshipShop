package com.example.starshipshop.domain.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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
    @NotBlank(message = "Address is mandatory and cannot be null, empty or blank")
    private String address;
    @NonNull
    @Positive
    private Long zipCode;
    @NotBlank(message = "City is mandatory and cannot be null, empty or blank")
    private String city;
    @NotBlank(message = "State is mandatory and cannot be null, empty or blank")
    private String state;
    @NotBlank(message = "Country is mandatory and cannot be null, empty or blank")
    private String country;
    @NotBlank(message = "Planet is mandatory and cannot be null, empty or blank")
    private String planet;
}
