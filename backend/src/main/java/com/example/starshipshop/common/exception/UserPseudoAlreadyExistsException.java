package com.example.starshipshop.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserPseudoAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserPseudoAlreadyExistsException(String pseudo) {
        super("Pseudo " + pseudo + " is already used for this account.");
    }
}
