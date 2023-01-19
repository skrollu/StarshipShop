package com.starshipshop.starshipservice.domain.weapon;

import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.starshipshop.starshipservice.domain.manufacturer.ManufacturerOutput;
import com.starshipshop.starshipservice.service.mapper.serializer.IdCombinedSerializer.IdDeserializer;
import com.starshipshop.starshipservice.service.mapper.serializer.IdCombinedSerializer.IdSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Relation(collectionRelation = "weapons", itemRelation = "weapon")
public class WeaponOutput {
	@JsonSerialize(using = IdSerializer.class)
	@JsonDeserialize(using = IdDeserializer.class)
	private Long id;
	private String name;
	private ManufacturerOutput manufacturer;
}
