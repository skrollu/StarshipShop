package com.starshipshop.starshipservice.domain.hyperdriveSystem;

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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HyperdriveSystemInput {

    @NotBlank(message = "Name is mandatory and cannot be null, empty or blank")
    private String name;
    @Nullable
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(using = IdDeserializer.class)
    private Long manufacturerId;

}
