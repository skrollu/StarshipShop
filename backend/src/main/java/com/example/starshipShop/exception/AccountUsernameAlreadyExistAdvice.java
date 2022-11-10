package com.example.starshipshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AccountUsernameAlreadyExistAdvice {
	
	@ResponseBody
	@ExceptionHandler(AccountUsernameAlreadyExistException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String accountUsernameAlreadyExistHandler(AccountUsernameAlreadyExistException ex) {
		return "ADVICE: " + ex.getMessage();
	}
}
