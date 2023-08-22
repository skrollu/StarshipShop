package com.starshipshop.productservice.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weapon {
    private String name;
    private Manufacturer manufacturer;
}
