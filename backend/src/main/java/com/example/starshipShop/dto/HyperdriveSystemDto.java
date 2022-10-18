package com.example.starshipShop.dto;

import org.springframework.hateoas.server.core.Relation;
import com.example.starshipShop.service.mapper.serializer.IdCombinedSerializer.IdDeserializer;
import com.example.starshipShop.service.mapper.serializer.IdCombinedSerializer.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Relation(collectionRelation = "hyperdriveSystems", itemRelation = "hyperdriveSystem")
public class HyperdriveSystemDto {
	@JsonSerialize(using = IdSerializer.class)
	@JsonDeserialize(using = IdDeserializer.class)
	private Long id;
	private String name;
	private ManufacturerDto manufacturer;
}
