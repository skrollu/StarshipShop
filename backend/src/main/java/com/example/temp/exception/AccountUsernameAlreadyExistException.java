package com.example.starshipshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccountUsernameAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AccountUsernameAlreadyExistException(String message) {
		super(message);
	}
}
