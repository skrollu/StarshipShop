package com.example.starshipshop.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.starshipshop.common.exception.AccountUsernameAlreadyExistException;
import com.example.starshipshop.common.exception.NonMatchingPasswordException;
import com.example.starshipshop.common.exception.ResourceNotFoundException;
import com.example.starshipshop.common.exception.TooManyUserPerAccountException;
import com.example.starshipshop.common.exception.UserPseudoAlreadyExistsException;

@ControllerAdvice
public class GenericAdviceController {

    @ResponseBody
    @ExceptionHandler(AccountUsernameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String accountUsernameAlreadyExistHandler(AccountUsernameAlreadyExistException ex) {
        return "AccountUsernameAlreadyExistException: ADVICE: " + ex.getMessage();
    }
    
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String nullPointerHandler(MethodArgumentNotValidException ex) {
        return "MethodArgumentNotValidException: ADVICE: " + ex.getMessage();
    }
    
    @ResponseBody
    @ExceptionHandler(NonMatchingPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String nonMatchingPasswordHandler(NonMatchingPasswordException ex) {
        return "NonMatchingPasswordException: ADVICE: " + ex.getMessage();
    }
    
    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String resourceNotFoundHandler(ResourceNotFoundException ex) {
        return "ResourceNotFoundException: ADVICE: " + ex.getMessage();
    }
    
    @ResponseBody
    @ExceptionHandler(TooManyUserPerAccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String resourceNotFoundHandler(TooManyUserPerAccountException ex) {
        return "TooManyUserPerAccountException: ADVICE: " + ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserPseudoAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String resourceNotFoundHandler(UserPseudoAlreadyExistsException ex) {
        return "UserPseudoAlreadyExistsException: ADVICE: " + ex.getMessage();
    }
    
}
