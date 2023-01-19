package com.starshipshop.starshipservice.domain.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserTelephoneInput {
    @NotBlank(message = "Telephone number is mandatory and cannot be null, empty or blank")
    @Size(min = 10, max = 10)
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String telephoneNumber;
}
