package com.tattoostudio.api.exceptions;

public class StudioNotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 1;

    public StudioNotFoundException(String message) {
        super(message);
    }
}