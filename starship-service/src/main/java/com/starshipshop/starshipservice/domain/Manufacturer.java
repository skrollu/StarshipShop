package com.starshipshop.starshipservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Manufacturer {
    private Long id;
    private String name;
}
