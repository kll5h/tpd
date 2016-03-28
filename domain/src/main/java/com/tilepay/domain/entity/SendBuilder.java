package com.tilepay.domain.entity;

public class SendBuilder extends MessageBuilder<SendBuilder> {

    public static SendBuilder aSend() {
        return new SendBuilder();
    }

    @Override
    public Send build() {
        Send object = new Send();
        object.setAsset(asset);
        object.setQuantity(quantity);
        return object;
    }

    @Override
    protected SendBuilder getThis() {
        return this;
    }
}
