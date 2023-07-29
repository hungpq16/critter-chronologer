package com.udacity.jdnd.course3.critter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Pet is not found, please try again!")
public class PetNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PetNotFoundException(String message) {
        super(message);
    }
}
