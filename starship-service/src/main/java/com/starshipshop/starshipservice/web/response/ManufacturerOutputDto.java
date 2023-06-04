package com.starshipshop.starshipservice.web.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@Relation(collectionRelation = "manufacturers", itemRelation = "manufacturer")
public class ManufacturerOutputDto {
    private Long id;
    private String name;
}
