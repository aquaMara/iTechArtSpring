package org.aquam.learnrest.exception.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.aquam.learnrest.exception.AppResponse;
import org.aquam.learnrest.exception.EmptyInputException;
import org.aquam.learnrest.exception.EntitiesNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.IOException;
import java.time.ZonedDateTime;


@ControllerAdvice
// @RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<AppResponse> handleUsernameNotFoundException(AuthenticationException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppResponse> handleEntityNotFoundException(PersistenceException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }

    @ExceptionHandler(EntitiesNotFoundException.class)
    public ResponseEntity<AppResponse> handleEntitiesNotFoundException(RuntimeException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<AppResponse> handleEntityExistsException(PersistenceException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<AppResponse> handleIOException(Exception exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);     // 500
    }

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<AppResponse> handleEmptyInputException(RuntimeException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  // 400 некорректный запрос
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AppResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.BAD_REQUEST, exception.getConstraintViolations());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AppResponse> handleBadCredentialsException(AuthenticationException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({JwtException.class, IllegalArgumentException.class})
    public ResponseEntity<AppResponse> handleRuntimeException(RuntimeException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
/*
@ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<AppResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }

    @ExceptionHandler(EntitiesNotFoundException.class)
    public ResponseEntity<AppResponse> handleEntitiesNotFoundException(EntitiesNotFoundException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<AppResponse> handleEntityExistsException(EntityExistsException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<AppResponse> handleIOException(IOException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);     // 500
    }

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<AppResponse> handleEmptyInputException(EmptyInputException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  // 400 некорректный запрос
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AppResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.BAD_REQUEST, exception.getConstraintViolations());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AppResponse> handleBadCredentialsException(BadCredentialsException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<AppResponse> handleJwtException(JwtException exception) {
        AppResponse response = new AppResponse("Token is not valid", ZonedDateTime.now(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AppResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        AppResponse response = new AppResponse("Token is null", ZonedDateTime.now(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
 */

/*
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<AppResponse> handleNullPointerException(NullPointerException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
 */
