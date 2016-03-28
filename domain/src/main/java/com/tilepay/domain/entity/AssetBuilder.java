package com.tilepay.domain.entity;

import java.math.BigDecimal;

public class AssetBuilder implements Builder<Asset> {

    private String assetId;

    private String name;

    private BigDecimal minersFee;

    private String protocol;

    private Boolean divisible;

    private Boolean booked;

    public AssetBuilder setAssetId(String assetId) {
        this.assetId = assetId;
        return this;
    }

    public AssetBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public AssetBuilder setMinersFee(BigDecimal minersFee) {
        this.minersFee = minersFee;
        return this;
    }

    public AssetBuilder setDivisible(Boolean divisible) {
        this.divisible = divisible;
        return this;
    }

    public AssetBuilder setBooked(Boolean booked) {
        this.booked = booked;
        return this;
    }

    @Deprecated
    public AssetBuilder setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public AssetBuilder setProtocol(Protocol protocol) {
        return setProtocol(protocol.name());
    }

    public static AssetBuilder anAsset() {
        return new AssetBuilder();
    }

    @Override
    public Asset build() {
        Asset object = new Asset();
        object.setAssetId(assetId);
        object.setName(name);
        object.setMinersFee(minersFee);
        object.setProtocol(protocol);
        object.setDivisible(divisible);
        object.setBooked(booked);
        return object;
    }

    public Asset buildCntrprtyTilecoinx() {
        return anAsset().setName(Asset.CNTRPRTY_TILECOINX).setDivisible(true).setBooked(true).build();
    }

    public AssetBuilder setTilecoinProtocol() {
        return setProtocol(Protocol.TILECOIN);
    }

    public AssetBuilder setCntrprtyProtocol() {
        return setProtocol(Protocol.CNTRPRTY);
    }

    public AssetBuilder setTilecoinx() {
        return setName(CurrencyEnum.TILECOINXTC.name());
    }
}
