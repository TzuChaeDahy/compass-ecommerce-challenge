package com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultException;
import java.util.Map;

public class NotEnoughStockException extends DefaultException {
    public NotEnoughStockException(String message) {
        super(Map.of("stock", message));
    }
    
}
