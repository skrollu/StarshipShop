package com.starshipshop.starshipservice.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NonMatchingPasswordException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NonMatchingPasswordException() {
        super("The given passwords and matching password are different.");
    }
}
