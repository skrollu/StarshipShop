package com.starshipshop.starshipservice.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManufacturerOutputDto {
    private Long id;
    private String name;
}
