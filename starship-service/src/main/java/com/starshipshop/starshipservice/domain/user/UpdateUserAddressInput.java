package com.starshipshop.starshipservice.domain.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.lang.NonNull;
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
public class UpdateUserAddressInput {
    @Nullable // Entity given without id is created
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(using = IdDeserializer.class)
    private Long id;
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
