package com.example.order_management.model.exception;

public class PurchaseOrderNotFoundException extends RuntimeException {

    public PurchaseOrderNotFoundException() {
    }

    public PurchaseOrderNotFoundException(String message) {

        super(message);

    }

}
