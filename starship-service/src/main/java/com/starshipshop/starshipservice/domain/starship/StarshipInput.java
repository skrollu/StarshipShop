package com.starshipshop.starshipservice.domain.starship;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.starshipshop.starshipservice.service.mapper.serializer.IdCombinedSerializer.IdDeserializer;
import com.starshipshop.starshipservice.service.mapper.serializer.IdCombinedSerializer.IdSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StarshipInput {
    @NotBlank(message = "Name is mandatory and cannot be null, empty or blank")
    private String name;
    private String engines;
    private double height;
    private double width;
    private double lenght;
    private double weight;
    private String description;
    @Nullable
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(using = IdDeserializer.class)
    private Long manufacturerId;
    @Nullable
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(using = IdDeserializer.class)
    private Long hyperdriveSystemId;
    @Nullable
    @Size(min = 0)
    @Valid
    private List<StarshipWeaponInput> weapons;
}
