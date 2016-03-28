package com.tilepay.daemon.service;

public class AssetDoesNotExistException extends RuntimeException {

    public AssetDoesNotExistException() {
    }

    public AssetDoesNotExistException(String message) {
        super(message);
    }
}
