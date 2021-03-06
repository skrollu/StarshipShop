package com.example.starshipShop.mapper.converter;

import org.hashids.Hashids;

import com.example.starshipShop.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.util.StdConverter;

public class HashToIdConverter extends StdConverter<String, Long> {

	public static Long convertToId(String hash) {
		Hashids hashids = new Hashids("mySuperSalt", 8);
		long[] numbers = hashids.decode(hash);
		if (numbers.length == 0) {
			throw new ResourceNotFoundException("Cannot find resource with the given id: " + hash);
		}
		return numbers[0];
	}

	@Override
	public Long convert(String hash) {
		return convertToId(hash);
	}
}
