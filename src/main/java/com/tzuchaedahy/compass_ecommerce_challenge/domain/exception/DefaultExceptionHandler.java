package com.tzuchaedahy.compass_ecommerce_challenge.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler.exceptions.EmptyNameException;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler.exceptions.NoItemsBoughtException;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler.exceptions.UnableToBuyNoItemsException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.exception.UnableToCreateBuyException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.exception.UnableToCreateClientException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.exception.UnableToCreateProductException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions.NotEnoughStockException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions.ProductAlreadyRegisteredException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions.UnableToAuthenticateClientException;
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

    @ExceptionHandler(UnableToAuthenticateClientException.class)
    public ResponseEntity<DefaultError> handleCouldNotAuthenticateClientException(UnableToAuthenticateClientException exception) {

        return new ResponseEntity<>(new DefaultError(exception.getMessages()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyRegisteredException.class)
    public ResponseEntity<DefaultError> handleProductAlreadyRegisteredException(ProductAlreadyRegisteredException exception) {

        return new ResponseEntity<>(new DefaultError(exception.getMessages()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnableToBuyNoItemsException.class)
    public ResponseEntity<DefaultError> handleUnableToBuyNoItemsException(UnableToBuyNoItemsException exception) {

        return new ResponseEntity<>(new DefaultError(exception.getMessages()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<DefaultError> handleNotEnoughStockException(NotEnoughStockException exception) {

        return new ResponseEntity<>(new DefaultError(exception.getMessages()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyNameException.class)
    public ResponseEntity<DefaultError> handleEmptyNameException(EmptyNameException exception) {

        return new ResponseEntity<>(new DefaultError(exception.getMessages()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoItemsBoughtException.class)
    public ResponseEntity<DefaultError> handleNoItemsBoughtException(NoItemsBoughtException exception) {

        return new ResponseEntity<>(new DefaultError(exception.getMessages()), HttpStatus.NOT_FOUND);
    }
}
