package org.aquam.learnrest.exception;

public class EntitiesNotFoundException extends RuntimeException {

    public EntitiesNotFoundException() {
        super();
    }

    public EntitiesNotFoundException(String message) {
        super(message);
    }
}
