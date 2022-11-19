package com.example.starshipshop.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.starshipshop.exception.UserPseudoAlreadyExistsException;

@ControllerAdvice
public class UserPseudoAlreadyExistsAdvice {

	@ResponseBody
	@ExceptionHandler(UserPseudoAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String resourceNotFoundHandler(UserPseudoAlreadyExistsException ex) {
		return "ADVICE: " + ex.getMessage();
	}
}
