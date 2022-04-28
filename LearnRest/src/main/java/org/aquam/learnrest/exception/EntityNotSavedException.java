package org.aquam.learnrest.exception;

public class EntityNotSavedException extends RuntimeException {

    public EntityNotSavedException() {
        super();
    }

    public EntityNotSavedException(String message) {
        super(message);
    }
}
