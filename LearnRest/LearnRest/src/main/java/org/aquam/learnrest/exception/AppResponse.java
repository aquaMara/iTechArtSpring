package org.aquam.learnrest.exception;

import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import java.time.ZonedDateTime;
import java.util.Set;

public class AppResponse {

    private String message;
    private ZonedDateTime zonedDateTime;
    private HttpStatus httpStatus;
    private Set<ConstraintViolation<?>> validationExceptions;

    public AppResponse(String message, ZonedDateTime zonedDateTime, HttpStatus httpStatus) {
        this.message = message;
        this.zonedDateTime = zonedDateTime;
        this.httpStatus = httpStatus;
    }

    public AppResponse(String message, ZonedDateTime zonedDateTime, HttpStatus httpStatus, Set<ConstraintViolation<?>> validationExceptions) {
        this.message = message;
        this.zonedDateTime = zonedDateTime;
        this.httpStatus = httpStatus;
        this.validationExceptions = validationExceptions;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
