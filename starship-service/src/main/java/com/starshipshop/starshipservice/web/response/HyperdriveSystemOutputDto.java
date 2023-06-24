package com.starshipshop.starshipservice.web.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@Relation(collectionRelation = "hyperdriveSystems", itemRelation = "hyperdriveSystem")
public class HyperdriveSystemOutputDto {

    private Long id;
    private String name;
    private ManufacturerOutputDto manufacturer;
}
