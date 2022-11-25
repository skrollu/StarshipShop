package com.example.starshipshop.common.advice;

import javax.validation.UnexpectedTypeException;
import org.hibernate.jdbc.TooManyRowsAffectedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.starshipshop.common.exception.AccountUsernameAlreadyExistException;
import com.example.starshipshop.common.exception.NonMatchingPasswordException;
import com.example.starshipshop.common.exception.ResourceNotFoundException;
import com.example.starshipshop.common.exception.TooManyUserPerAccountException;
import com.example.starshipshop.common.exception.UserPseudoAlreadyExistsException;

@RestControllerAdvice
public class GenericAdviceController {
    
    @ExceptionHandler(AccountUsernameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String accountUsernameAlreadyExistHandler(AccountUsernameAlreadyExistException ex) {
        return "AccountUsernameAlreadyExistException: ADVICE: " + ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String nullPointerHandler(MethodArgumentNotValidException ex) {
        return "MethodArgumentNotValidException: ADVICE: " + ex.getMessage();
    }

    @ExceptionHandler(NonMatchingPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String nonMatchingPasswordHandler(NonMatchingPasswordException ex) {
        return "NonMatchingPasswordException: ADVICE: " + ex.getMessage();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String resourceNotFoundHandler(ResourceNotFoundException ex) {
        return "ResourceNotFoundException: ADVICE: " + ex.getMessage();
    }

    @ExceptionHandler(TooManyUserPerAccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String resourceNotFoundHandler(TooManyUserPerAccountException ex) {
        return "TooManyUserPerAccountException: ADVICE: " + ex.getMessage();
    }

    @ExceptionHandler(TooManyRowsAffectedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String resourceNotFoundHandler(TooManyRowsAffectedException ex) {
        return "TooManyRowsAffectedException: ADVICE: " + ex.getMessage();
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String resourceNotFoundHandler(UnexpectedTypeException ex) {
        return "UnexpectedTypeException: ADVICE: " + ex.getMessage();
    }

    @ExceptionHandler(UserPseudoAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String resourceNotFoundHandler(UserPseudoAlreadyExistsException ex) {
        return "UserPseudoAlreadyExistsException: ADVICE: " + ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String illegalArgumentHandler(IllegalArgumentException ex) {
        return "IllegalArgumentException: ADVICE: " + ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String nullPointerHandler(NullPointerException ex) {
        return "NullPointerException: ADVICE: " + ex.getMessage();
    }
}
