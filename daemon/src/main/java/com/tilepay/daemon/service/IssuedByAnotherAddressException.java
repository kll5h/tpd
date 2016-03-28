package com.tilepay.daemon.service;

public class IssuedByAnotherAddressException extends RuntimeException {

    public IssuedByAnotherAddressException(String message) {
        super(message);
    }
}
