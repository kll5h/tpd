package com.tilepay.core.dto;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.AssetBuilder;

public class OutputDTOBuilder {

    private Asset asset;

    private String assetAmount;

    private boolean isIssuance;

    private String address;

    private String amount;

    public static OutputDTOBuilder anOutput() {
        return new OutputDTOBuilder();
    }

    public OutputDTOBuilder withAssetName(String assetName) {
        this.asset = AssetBuilder.anAsset().setName(assetName).build();
        return this;
    }

    public OutputDTOBuilder withAssetAmount(String assetAmount) {
        this.assetAmount = assetAmount;
        return this;
    }

    public OutputDTOBuilder isIssuance(boolean isIssuance) {
        this.isIssuance = isIssuance;
        return this;
    }

    public OutputDTOBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public OutputDTOBuilder withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public OutputDto build() {
        OutputDto output = new OutputDto();
        output.setAsset(asset);
        output.setAssetAmount(assetAmount);
        output.setIssuance(isIssuance);
        output.setAddress(address);
        output.setAmount(amount);
        return output;
    }

}