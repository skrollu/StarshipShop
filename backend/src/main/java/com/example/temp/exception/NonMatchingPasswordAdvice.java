package com.example.starshipshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NonMatchingPasswordAdvice {
	
	@ResponseBody
	@ExceptionHandler(NonMatchingPasswordException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String nonMatchingPasswordHandler(NonMatchingPasswordException ex) {
		return "ADVICE: " + ex.getMessage();
	}
}
