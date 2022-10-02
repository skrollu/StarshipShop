package com.example.starshipShop.mapper.converter;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.databind.util.StdConverter;
import lombok.extern.slf4j.Slf4j;

@JsonComponent
@Slf4j
public class IdToHashConverter extends StdConverter<Long, String> {

	@Value("${app.hashids.salt}")
	private String HASHIDS_SALT_KEY;

	@Override
	public String convert(Long id) {
		Hashids hashids = new Hashids(HASHIDS_SALT_KEY, 8);
		String hash = hashids.encode(id);
		log.debug("Id " + id + " To Hash " + hash);
		return hash;	
	}
}
