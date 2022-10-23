package com.example.starshipShop.domain;

import java.util.List;
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
@Relation(collectionRelation = "starships", itemRelation = "starship")
public class StarshipDto {
	
	@JsonSerialize(using = IdSerializer.class)
	@JsonDeserialize(using = IdDeserializer.class)
	private Long id;
	private String name;
	private String engines;
	private double height;
	private double width;
	private double lenght;
	private double weight;
	private String description;
	private ManufacturerDto manufacturer;
	private HyperdriveSystemDto hyperdriveSystem;
	private List<WeaponDto> weapons;

}
