package com.starshipshop.starshipservice.domain.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.starshipshop.starshipservice.service.mapper.serializer.IdCombinedSerializer.IdDeserializer;
import com.starshipshop.starshipservice.service.mapper.serializer.IdCombinedSerializer.IdSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserEmailInput {
    @Nullable // Entity given without id is created
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(using = IdDeserializer.class)
    private Long id;
    @NotBlank(message = "Email is mandatory and cannot be null, empty or blank")
    @Email(message = "Email must respect a valid email")
    private String email;
}
