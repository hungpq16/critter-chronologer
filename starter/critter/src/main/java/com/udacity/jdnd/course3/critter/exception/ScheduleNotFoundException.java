package com.udacity.jdnd.course3.critter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Schedule is not found, please try again!")
public class ScheduleNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ScheduleNotFoundException(String message) {
        super(message);
    }
}