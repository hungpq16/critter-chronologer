package com.udacity.jdnd.course3.critter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Customer is not found, please try again!")
public class CustomerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomerNotFoundException(String message) {
        super(message);
    }
}