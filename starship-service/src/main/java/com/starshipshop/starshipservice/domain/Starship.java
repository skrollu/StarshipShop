package com.starshipshop.starshipservice.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Starship {

    private Long id;
    private String name;
    private String engines;
    private double height;
    private double width;
    private double lenght;
    private double weight;
    private String description;
    private Manufacturer manufacturer;
    private HyperdriveSystem hyperdriveSystem;
    private List<Weapon> weapons;
}
