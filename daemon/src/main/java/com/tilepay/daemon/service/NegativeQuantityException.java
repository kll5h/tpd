package com.tilepay.daemon.service;

public class NegativeQuantityException extends RuntimeException {

    public NegativeQuantityException() {
    }

    public NegativeQuantityException(String message) {
        super(message);
    }
}
