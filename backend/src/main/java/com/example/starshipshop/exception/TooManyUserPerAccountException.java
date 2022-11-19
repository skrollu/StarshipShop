package com.example.starshipshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TooManyUserPerAccountException extends RuntimeException {
    
    private final static int MAX_USER_COUNT_PER_ACCOUNT = 5;
    private static final long serialVersionUID = 1L;
    
    public TooManyUserPerAccountException() {
        super("There is already "+MAX_USER_COUNT_PER_ACCOUNT+" users on this account.");
    }    
}