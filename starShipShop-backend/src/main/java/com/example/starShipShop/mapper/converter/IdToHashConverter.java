package com.example.starshipShop.mapper.converter;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonComponent;
import com.fasterxml.jackson.databind.util.StdConverter;

@JsonComponent
public class IdToHashConverter extends StdConverter<Long, String> {

	@Value("${app.hashids.salt}")
	private String HASHIDS_SALT_KEY;

	@Override
	public String convert(Long id) {
		// TODO remove sysout
		System.out.println("HASH SALT: " + HASHIDS_SALT_KEY);
		Hashids hashids = new Hashids(HASHIDS_SALT_KEY, 8);
		String hash = hashids.encode(id);
		System.out.println("Id " + id + " To Hash " + hash);
		return hash;	
	}
}
