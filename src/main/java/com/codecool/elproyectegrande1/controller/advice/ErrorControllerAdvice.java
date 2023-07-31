package com.codecool.elproyectegrande1.controller.advice;

import com.codecool.elproyectegrande1.service.exceptions.DreamNotFoundException;
import com.codecool.elproyectegrande1.service.exceptions.MentorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler({DreamNotFoundException.class, MentorNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse handleNoDream(RuntimeException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    private record ErrorResponse(String errorMessage) {

    }
}
