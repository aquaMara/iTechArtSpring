package org.aquam.learnrest.exception;

public class EmptyInputException extends RuntimeException {

    public EmptyInputException() {
        super();
    }

    public EmptyInputException(String message) {
        super(message);
    }
}
