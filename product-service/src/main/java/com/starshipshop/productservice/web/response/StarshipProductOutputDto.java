package com.starshipshop.productservice.web.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class StarshipProductOutputDto {
    private String skuCode;
    private BigDecimal price;
    private String color;
    private String name;
    private String engines;
    private double height;
    private double width;
    private double length;
    private double weight;
    private String description;
    private ManufacturerOutputDto manufacturer;
    private HyperdriveSystemOutputDto hyperdriveSystem;
    private List<WeaponOutputDto> weapons;
}
