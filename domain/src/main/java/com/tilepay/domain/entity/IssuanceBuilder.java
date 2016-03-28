package com.tilepay.domain.entity;

public class IssuanceBuilder extends MessageBuilder<IssuanceBuilder> {

    private IssuanceStatus status;

    public static IssuanceBuilder anIssuance() {
        return new IssuanceBuilder();
    }

    public IssuanceBuilder setStatus(IssuanceStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public Issuance build() {
        Issuance object = new Issuance();
        object.setAsset(asset);
        object.setQuantity(quantity);
        object.setStatus(status);
        return object;
    }

    @Override
    protected IssuanceBuilder getThis() {
        return this;
    }
}
