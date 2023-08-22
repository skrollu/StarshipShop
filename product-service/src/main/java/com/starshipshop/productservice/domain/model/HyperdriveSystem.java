package com.starshipshop.productservice.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HyperdriveSystem {
    private String name;
    private Manufacturer manufacturer;
}
