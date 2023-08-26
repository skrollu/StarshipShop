package com.starshipshop.productservice.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class StarshipProduct {
    private Long id;
    private int quantity;
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
    private Manufacturer manufacturer;
    private HyperdriveSystem hyperdriveSystem;
    private List<Weapon> weapons;
}
