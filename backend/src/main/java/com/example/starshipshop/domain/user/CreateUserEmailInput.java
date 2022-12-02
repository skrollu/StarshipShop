package com.example.starshipshop.domain.user;

import javax.annotation.Nonnull;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserEmailInput {
    @Nonnull
    @Email
    private String email;
}
