package com.tilepay.core.dto;

import java.math.BigDecimal;

import com.tilepay.domain.entity.Asset;

public class TransactionDTOBuilder {

    private Asset asset = new Asset();
    private String minersFee;
    private String amount;
    private String destination;
    private String source;
    private String password;

    public static TransactionDTOBuilder aTransaction() {
        return new TransactionDTOBuilder();
    }

    public TransactionDTOBuilder withCurrency(String name) {
        this.asset.setName(name);
        return this;
    }

    public TransactionDTOBuilder setAsset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public TransactionDTOBuilder withCurrencyMinersFee(String name, BigDecimal minersFee) {
        this.asset.setName(name);
        this.asset.setMinersFee(minersFee);
        return this;
    }

    public TransactionDTOBuilder setSource(String source) {
        this.source = source;
        return this;
    }

    public TransactionDTOBuilder setMinersFee(String minersFee) {
        this.minersFee = minersFee;
        return this;
    }

    public TransactionDTOBuilder setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public TransactionDTOBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public TransactionDTOBuilder withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public TransactionDto build() {
        TransactionDto transaction = new TransactionDto();
        transaction.setAsset(asset);
        transaction.setMinersFee(minersFee);
        transaction.setAmount(amount);
        transaction.setAddressTo(destination);
        transaction.setAddressFrom(source);
        transaction.setPassword(password);
        return transaction;
    }

}