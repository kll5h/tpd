package com.tilepay.domain.entity;

public class TransactionBuilder implements Builder<Transaction> {

    private String source;
    private String destination;
    private Message message;
    private String hash;
    private byte[] data;

    public static TransactionBuilder aTransaction() {
        return new TransactionBuilder();
    }

    public TransactionBuilder setSource(String source) {
        this.source = source;
        return this;
    }

    public TransactionBuilder setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public TransactionBuilder setMessage(Message message) {
        this.message = message;
        return this;
    }

    public TransactionBuilder setHash(String hash) {
        this.hash = hash;
        return this;
    }

    public TransactionBuilder setData(byte[] data) {
        this.data = data;
        return this;
    }

    @Override
    public Transaction build() {
        Transaction transaction = new Transaction();
        transaction.setSource(source);
        transaction.setDestination(destination);
        transaction.setMessage(message);
        transaction.setHash(hash);
        transaction.setData(data);
        return transaction;
    }

}
