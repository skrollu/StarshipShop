package com.example.starshipShop.mapper.converter;

import org.hashids.Hashids;

import com.fasterxml.jackson.databind.util.StdConverter;

public class IdToHashConverter extends StdConverter<Long, String> {

	public static String convertToHash(Long id) {
		Hashids hashids = new Hashids("mySuperSalt", 8);
		String hash = hashids.encode(id);
//		if (hash == null || hash.isEmpty()) {
//			throw new ResourceNotFoundException("Cannot encode the given id");
//		}
		System.out.println("Id " + id + " To Hash " + hash);
		return hash;
	}

	@Override
	public String convert(Long id) {
		return convertToHash(id);
	}
}
