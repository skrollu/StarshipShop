package com.starshipshop.orderservice.web.advice;

import com.starshipshop.orderservice.common.exception.ResourceNotFoundException;
import com.starshipshop.orderservice.web.response.ErrorOutputDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFound(RuntimeException ex, WebRequest request) {
        ErrorOutputDto body = new ErrorOutputDto("ResourceNotFoundException", "001", ex.getMessage());

        ResponseEntity<Object> objectResponseEntity = handleExceptionInternal(ex, body,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);

        return objectResponseEntity;
    }
}
