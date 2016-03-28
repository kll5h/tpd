package com.tilepay.domain.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BalanceBuilder implements Builder<Balance> {

    private String address;
    private Asset asset;
    private BigInteger quantity;
    private BigInteger estimatedQuantity;

    public BalanceBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public BalanceBuilder setAsset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public BalanceBuilder setQuantity(BigInteger quantity) {
        this.quantity = quantity;
        return this;
    }

    public BalanceBuilder setQuantity(Integer quantity) {
        return setQuantity(BigInteger.valueOf(quantity));
    }

    public BalanceBuilder setQuantity(Long quantity) {
        return setQuantity(BigInteger.valueOf(quantity));
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity.toBigInteger();
    }

    public BalanceBuilder setEstimatedQuantity(BigInteger estimatedQuantity) {
        this.estimatedQuantity = estimatedQuantity;
        return this;
    }

    public BalanceBuilder setEstimatedQuantity(Long estimatedQuantity) {
        return setEstimatedQuantity(BigInteger.valueOf(estimatedQuantity));
    }

    public static BalanceBuilder aBalance() {
        return new BalanceBuilder();
    }

    @Override
    public Balance build() {
        Balance object = new Balance();
        object.setAddress(address);
        object.setAsset(asset);
        object.setQuantity(quantity);
        object.setEstimatedQuantity(estimatedQuantity);
        return object;
    }


}
