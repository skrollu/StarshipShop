package com.starshipshop.starshipservice.web.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@Relation(collectionRelation = "weapons", itemRelation = "weapon")
public class WeaponOutputDto {

    private Long id;
    private String name;
    private ManufacturerOutputDto manufacturer;
}
