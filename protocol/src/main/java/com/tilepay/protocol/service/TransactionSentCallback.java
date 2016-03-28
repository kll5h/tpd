package com.tilepay.protocol.service;


public interface TransactionSentCallback {

    void finish(String transactionHash);
}
