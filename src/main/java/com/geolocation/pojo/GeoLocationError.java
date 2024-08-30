package com.geolocation.pojo;

public class GeoLocationError {
    private String message;
    private Exception exception;

    public GeoLocationError(String message) {
        this.message = message;
    }

    public GeoLocationError(String message, Exception exception) {
        this.message = message;
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public String toString() {
        if (exception != null) {
            return message + " (Exception: " + exception.getMessage() + ")";
        }
        return message;
    }
}
