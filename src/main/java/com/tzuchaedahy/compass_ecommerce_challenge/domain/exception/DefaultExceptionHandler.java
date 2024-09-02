package com.tzuchaedahy.compass_ecommerce_challenge.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.exception.UnableToCreateBuyException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.exception.UnableToCreateClientException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.exception.UnableToCreateProductException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions.UserAlreadyRegisteredException;

@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(UnableToCreateClientException.class)
    public ResponseEntity<DefaultError> handleUnableToCreateClientException(UnableToCreateClientException exception) {

        return new ResponseEntity<>(new DefaultError(exception.getMessages()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnableToCreateBuyException.class)
    public ResponseEntity<DefaultError> handleUnableToCreateBuyException(UnableToCreateBuyException exception) {

        return new ResponseEntity<>(new DefaultError(exception.getMessages()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnableToCreateProductException.class)
    public ResponseEntity<DefaultError> handleUnableToCreateProductException(UnableToCreateProductException exception) {

        return new ResponseEntity<>(new DefaultError(exception.getMessages()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<DefaultError> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException exception) {

        return new ResponseEntity<>(new DefaultError(exception.getMessages()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<DefaultError> handleUsernameNotFoundException(UsernameNotFoundException exception) {

        return new ResponseEntity<>(new DefaultError(exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
