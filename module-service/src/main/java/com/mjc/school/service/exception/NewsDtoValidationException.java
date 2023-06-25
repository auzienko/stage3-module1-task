package com.mjc.school.service.exception;

import lombok.Getter;

@Getter
public class NewsDtoValidationException extends RuntimeException {
    private final String field;
    private final String message;
    private final String value;


    public NewsDtoValidationException(String field, String message, String value) {
        this.field = field;
        this.message = message;
        this.value = value;
    }
}
