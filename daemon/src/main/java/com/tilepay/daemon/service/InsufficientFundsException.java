package com.tilepay.daemon.service;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
