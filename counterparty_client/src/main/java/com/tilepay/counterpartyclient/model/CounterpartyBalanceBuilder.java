package com.tilepay.counterpartyclient.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CounterpartyBalanceBuilder {

    private String asset;

    private BigInteger quantity = BigInteger.ZERO;

    private long estimatedQuantity;

    public static CounterpartyBalanceBuilder aCounterpartyBalance() {
        return new CounterpartyBalanceBuilder();
    }

    public CounterpartyBalanceBuilder setAsset(String asset) {
        this.asset = asset;
        return this;
    }

    public CounterpartyBalanceBuilder setQuantity(BigInteger quantity) {
        this.quantity = quantity;
        return this;
    }

    public CounterpartyBalanceBuilder setEstimatedQuantity(long estimatedQuantity) {
        this.estimatedQuantity = estimatedQuantity;
        return this;
    }

    public CounterpartyBalance build() {
        CounterpartyBalance object = new CounterpartyBalance();
        object.setAsset(asset);
        //object.setQuantity(quantity);
        object.setQuantity(new BigDecimal(quantity));
        object.setEstimatedQuantity(estimatedQuantity);
        return object;
    }

}