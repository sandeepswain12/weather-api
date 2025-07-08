package com.weatherapp.exception;

public class PincodeNotFoundException extends RuntimeException {
    public PincodeNotFoundException(String message) {
        super(message);
    }
}
