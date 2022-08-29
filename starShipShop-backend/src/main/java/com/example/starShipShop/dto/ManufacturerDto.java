package com.example.starshipShop.dto;

import org.springframework.hateoas.server.core.Relation;
import com.example.starshipShop.mapper.serializer.IdCombinedSerializer.IdDeserializer;
import com.example.starshipShop.mapper.serializer.IdCombinedSerializer.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Relation(collectionRelation = "manufacturers", itemRelation = "manufacturer")
@NonNull
public class ManufacturerDto {
	
	@JsonSerialize(using = IdSerializer.class)
	@JsonDeserialize(using = IdDeserializer.class)
	private Long id;
	private String name;
}
