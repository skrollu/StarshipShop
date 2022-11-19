package com.example.starshipshop.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.starshipshop.common.exception.NonMatchingPasswordException;

@ControllerAdvice
public class NonMatchingPasswordAdvice {
	
	@ResponseBody
	@ExceptionHandler(NonMatchingPasswordException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String nonMatchingPasswordHandler(NonMatchingPasswordException ex) {
		return "ADVICE: " + ex.getMessage();
	}
}
