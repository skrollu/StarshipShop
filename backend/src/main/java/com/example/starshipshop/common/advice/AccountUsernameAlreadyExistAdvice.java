package com.example.starshipshop.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.starshipshop.common.exception.AccountUsernameAlreadyExistException;

@ControllerAdvice
public class AccountUsernameAlreadyExistAdvice {
	
	@ResponseBody
	@ExceptionHandler(AccountUsernameAlreadyExistException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String accountUsernameAlreadyExistHandler(AccountUsernameAlreadyExistException ex) {
		return "AccountUsernameAlreadyExistException: ADVICE: " + ex.getMessage();
	}
}
