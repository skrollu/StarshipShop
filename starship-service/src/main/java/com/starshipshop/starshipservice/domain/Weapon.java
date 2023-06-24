package com.starshipshop.starshipservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weapon {

    private Long id;
    private String name;
    private Manufacturer manufacturer;
}
