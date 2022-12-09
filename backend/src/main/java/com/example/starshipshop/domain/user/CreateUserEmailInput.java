package com.example.starshipshop.domain.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserEmailInput {

    @NotBlank(message = "Email is mandatory and cannot be null, empty or blank")
    @Email(message = "Email must respect a valid email")
    private String email;
}
