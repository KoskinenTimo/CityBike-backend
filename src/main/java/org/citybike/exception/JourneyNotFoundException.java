package org.citybike.exception;

public class JourneyNotFoundException extends Exception {
    public JourneyNotFoundException(String message) {
        super(message);
    }
}
