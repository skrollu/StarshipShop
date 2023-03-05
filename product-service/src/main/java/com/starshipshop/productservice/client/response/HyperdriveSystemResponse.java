package com.starshipshop.productservice.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HyperdriveSystemResponse {
    private String id;
    private String name;
    private ManufacturerResponse manufacturer;
}
