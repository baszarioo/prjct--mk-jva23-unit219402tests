package com.tattoostudio.api.exceptions;

public class RatingNotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 2;
    public RatingNotFoundException(String message) {
        super(message);
    }
}