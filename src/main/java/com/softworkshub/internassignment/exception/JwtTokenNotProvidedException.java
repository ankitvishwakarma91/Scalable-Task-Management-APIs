package com.softworkshub.internassignment.exception;

public class JwtTokenNotProvidedException extends RuntimeException {
    public JwtTokenNotProvidedException(String message) {
        super(message);
    }
}