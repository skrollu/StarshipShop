package com.starshipshop.starshipservice.service.mapper.converter;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.starshipshop.starshipservice.common.exception.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@JsonComponent
@Slf4j
public class HashToIdConverter extends StdConverter<String, Long> {

	@Value("${app.hashids.salt}")
	private String HASHIDS_SALT_KEY; 

	@Override
	public Long convert(String hash) {
		Assert.hasLength(hash,  "Cannot convert the given null or empty hash.");
		Hashids hashids = new Hashids(HASHIDS_SALT_KEY, 8);
		long[] numbers = hashids.decode(hash);
		if (numbers.length == 0) {
			throw new ResourceNotFoundException("Cannot convert hash to id with the given hash: " + hash);
		}
		log.debug("Hash " + hash + " To Id " + numbers[0]);
		return numbers[0];
	}
}
