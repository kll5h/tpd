package com.tilepay.counterpartyclient.model;

public class AssetInfo {

    private Boolean divisible;
    private String asset;

    public Boolean getDivisible() {
        return divisible;
    }

    public void setDivisible(Boolean divisible) {
        this.divisible = divisible;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    @Override
    public String toString() {
        return "AssetInfo{" +
                "divisible=" + divisible +
                ", asset='" + asset + '\'' +
                '}';
    }
}
