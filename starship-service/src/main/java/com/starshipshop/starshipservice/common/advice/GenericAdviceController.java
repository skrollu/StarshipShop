package com.starshipshop.starshipservice.common.advice;

import javax.validation.UnexpectedTypeException;

import org.hibernate.jdbc.TooManyRowsAffectedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.starshipshop.starshipservice.common.exception.AccountUsernameAlreadyExistException;
import com.starshipshop.starshipservice.common.exception.NonMatchingPasswordException;
import com.starshipshop.starshipservice.common.exception.ResourceNotFoundException;
import com.starshipshop.starshipservice.common.exception.TooManyUserPerAccountException;
import com.starshipshop.starshipservice.common.exception.UserPseudoAlreadyExistsException;

@RestControllerAdvice
public class GenericAdviceController {

    @ExceptionHandler(AccountUsernameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String accountUsernameAlreadyExistHandler(AccountUsernameAlreadyExistException ex) {
        return "AccountUsernameAlreadyExistException: ADVICE: " + ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String methodArgumentNotValidHandler(MethodArgumentNotValidException ex) {
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
    String tooManyUserPerAccountHandler(TooManyUserPerAccountException ex) {
        return "tooManyUserPerAccountException: ADVICE: " + ex.getMessage();
    }

    @ExceptionHandler(TooManyRowsAffectedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String tooManyRowsAffecteHandler(TooManyRowsAffectedException ex) {
        return "TooManyRowsAffectedException: ADVICE: " + ex.getMessage();
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String unexpectedTypeHandler(UnexpectedTypeException ex) {
        return "UnexpectedTypeException: ADVICE: " + ex.getMessage();
    }

    @ExceptionHandler(UserPseudoAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userPseudoAlreadyExistsHandler(UserPseudoAlreadyExistsException ex) {
        return "UserPseudoAlreadyExistsException: ADVICE: " + ex.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String illegalArgumentHandler(IllegalArgumentException ex) {
        return "IllegalArgumentException: ADVICE: " + ex.getMessage();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String nullPointerHandler(NullPointerException ex) {
        return "NullPointerException: ADVICE: " + ex.getMessage();
    }

    public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(
                MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
            return new ResponseEntity<>("MethodArgumentNotValidException: ADVICE: " + ex.getMessage(), headers, status);
        }
    }
}