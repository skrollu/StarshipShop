package com.example.starshipShop.mapper.converter;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonComponent;

import com.example.starshipShop.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.util.StdConverter;

@JsonComponent
public class HashToIdConverter extends StdConverter<String, Long> {

	@Value("${app.hashids.salt}")
	private String HASHIDS_SALT_KEY; 

	@Override
	public Long convert(String hash) {
		// TODO remove sysout
		System.out.println("HASH SALT: " + HASHIDS_SALT_KEY);
		Hashids hashids = new Hashids(HASHIDS_SALT_KEY, 8);
		long[] numbers = hashids.decode(hash);
		if (numbers.length == 0) {
			throw new ResourceNotFoundException("Cannot find convert hash to id with the given id: " + hash);
		}
		System.out.println("Hash " + hash + " To Id " + numbers[0]);
		return numbers[0];
	}
}
