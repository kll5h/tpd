package com.tilepay.core.model;

import java.math.BigDecimal;

public class AssetTransactionBuilder {

    private Address address;
    private String hash;
    private BigDecimal amount;

    public static AssetTransactionBuilder aAssetTransaction() {
        return new AssetTransactionBuilder();
    }

    public AssetTransactionBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public AssetTransactionBuilder withHash(String hash) {
        this.hash = hash;
        return this;
    }

    public AssetTransactionBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public AssetTransaction build() {
        AssetTransaction assetTransaction = new AssetTransaction();
        assetTransaction.setAddress(address);
        assetTransaction.setHash(hash);
        assetTransaction.setAmount(amount);
        return assetTransaction;
    }
}
