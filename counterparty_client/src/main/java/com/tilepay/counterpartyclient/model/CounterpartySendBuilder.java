package com.tilepay.counterpartyclient.model;

import java.math.BigDecimal;

public class CounterpartySendBuilder {

    private BigDecimal quantity;
    private String destination;
    private String tx_hash;
    private String source;
    private String asset;
    private String status;

    public CounterpartySendBuilder setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public CounterpartySendBuilder setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public CounterpartySendBuilder setTx_hash(String tx_hash) {
        this.tx_hash = tx_hash;
        return this;
    }

    public CounterpartySendBuilder setSource(String source) {
        this.source = source;
        return this;
    }

    public CounterpartySendBuilder setAsset(String asset) {
        this.asset = asset;
        return this;
    }

    public CounterpartySendBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public static CounterpartySendBuilder aCounterpartySend() {
        return new CounterpartySendBuilder();
    }

    public CounterpartySend build() {
        CounterpartySend object = new CounterpartySend();
        object.setQuantity(quantity);
        object.setDestination(destination);
        object.setTx_hash(tx_hash);
        object.setSource(source);
        object.setAsset(asset);
        object.setStatus(status);
        return object;
    }

}