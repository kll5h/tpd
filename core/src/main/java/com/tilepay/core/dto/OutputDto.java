package com.tilepay.core.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.tilepay.domain.entity.Asset;

public class OutputDto {

    @NotBlank
    private String address;

    @NotBlank
    private String amount;

    private Asset asset;

    private String assetAmount;

    private boolean isIssuance;
    
    private boolean isDeviceRegistration;
    
    private boolean isChange;

    public boolean isDeviceRegistration() {
        return isDeviceRegistration;
    }

    public void setDeviceRegistration(boolean isDeviceRegistration) {
        this.isDeviceRegistration = isDeviceRegistration;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public String getAssetAmount() {
        return assetAmount;
    }

    public void setAssetAmount(String assetAmount) {
        this.assetAmount = assetAmount;
    }

    public boolean isIssuance() {
        return isIssuance;
    }

    public void setIssuance(boolean isIssuance) {
        this.isIssuance = isIssuance;
    }

    public boolean isAssetExists() {
        return asset != null && assetAmount != null;
    }
    
    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean isChange) {
        this.isChange = isChange;
    }

    public String toString() {
        return assetAmount + " " + asset.getName();
    }

}
