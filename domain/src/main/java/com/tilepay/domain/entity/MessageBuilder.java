package com.tilepay.domain.entity;

import java.math.BigInteger;

public abstract class MessageBuilder<T extends MessageBuilder> implements Builder {

    protected Asset asset;
    protected BigInteger quantity;

    protected abstract T getThis();

    public T setAsset(Asset asset) {
        this.asset = asset;
        return getThis();
    }

    public T setQuantity(BigInteger quantity) {
        this.quantity = quantity;
        return getThis();
    }

    public T setQuantity(Integer quantity) {
        return setQuantity(BigInteger.valueOf(quantity));
    }

}
