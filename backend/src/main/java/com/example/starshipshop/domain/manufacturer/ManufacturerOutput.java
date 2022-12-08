package com.example.starshipshop.domain.manufacturer;

import org.springframework.hateoas.server.core.Relation;

import com.example.starshipshop.service.mapper.serializer.IdCombinedSerializer.IdDeserializer;
import com.example.starshipshop.service.mapper.serializer.IdCombinedSerializer.IdSerializer;
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
@Relation(collectionRelation = "manufacturers", itemRelation = "manufacturer")
public class ManufacturerOutput {

	@JsonSerialize(using = IdSerializer.class)
	@JsonDeserialize(using = IdDeserializer.class)
	private Long id;
	private String name;
}
