package com.starshipshop.starshipservice.domain.manufacturer;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ManufacturerInput {

    @NotBlank(message = "Name is mandatory and cannot be null, empty or blank")
    private String name;
}
