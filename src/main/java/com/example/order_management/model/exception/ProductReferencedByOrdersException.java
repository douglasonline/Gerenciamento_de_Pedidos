package com.example.order_management.model.exception;

public class ProductReferencedByOrdersException extends RuntimeException {

    public ProductReferencedByOrdersException() {
    }

    public ProductReferencedByOrdersException(String message) {
        super(message);
    }
}
