package org.citybike.exception;

public class RequiredResourceNotFoundWithIdException extends Exception {
    public RequiredResourceNotFoundWithIdException (String message) {
        super(message);
    }
}
