package com.starshipshop.productservice.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HyperdriveSystemOutputDto {
    private String name;
    private ManufacturerOutputDto manufacturer;
}
