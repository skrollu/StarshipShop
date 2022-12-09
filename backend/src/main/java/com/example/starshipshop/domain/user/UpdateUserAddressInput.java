package com.example.starshipshop.domain.user;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.example.starshipshop.service.mapper.serializer.IdCombinedSerializer.IdDeserializer;
import com.example.starshipshop.service.mapper.serializer.IdCombinedSerializer.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserAddressInput {
    @Nullable // Entity given without id is created
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(using = IdDeserializer.class)
    private Long id;
    @NotBlank(message = "Address is mandatory and cannot be null, empty or blank")
    private String address;
    @Nonnull
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
