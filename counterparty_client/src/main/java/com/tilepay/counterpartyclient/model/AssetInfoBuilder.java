package com.tilepay.counterpartyclient.model;

public class AssetInfoBuilder {

    private Boolean divisible;
    private String asset;

    public static AssetInfoBuilder anAssetInfo() {
        return new AssetInfoBuilder();
    }

    public AssetInfoBuilder setAsset(String asset) {
        this.asset = asset;
        return this;
    }

    public AssetInfoBuilder setDivisible(Boolean divisible) {
        this.divisible = divisible;
        return this;
    }

    public AssetInfo build() {
        AssetInfo object = new AssetInfo();
        object.setAsset(asset);
        object.setDivisible(divisible);
        return object;
    }
}
