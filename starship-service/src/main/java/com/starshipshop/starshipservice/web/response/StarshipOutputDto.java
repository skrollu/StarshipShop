package com.starshipshop.starshipservice.web.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Builder
@Data
@Relation(collectionRelation = "starships", itemRelation = "starship")
public class StarshipOutputDto {
    private Long id;
    private String name;
    private String engines;
    private double height;
    private double width;
    private double lenght;
    private double weight;
    private String description;
    private ManufacturerOutputDto manufacturer;
    private HyperdriveSystemOutputDto hyperdriveSystem;
    private List<WeaponOutputDto> weapons;
}
