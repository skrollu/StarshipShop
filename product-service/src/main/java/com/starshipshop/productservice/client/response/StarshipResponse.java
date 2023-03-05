package com.starshipshop.productservice.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StarshipResponse {

    private String id;
    private String name;
    private String engines;
    private double height;
    private double width;
    private double lenght;
    private double weight;
    private String description;
    private ManufacturerResponse manufacturer;
    private HyperdriveSystemResponse hyperdriveSystem;
    private List<WeaponResponse> weapons;
}
