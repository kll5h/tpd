package com.tilepay.core.dto;

import java.util.Date;

import com.tilepay.core.model.AssetCategory;

public class AssetIssuanceDto {

    private String source;

    private String issuer;

    private String assetName;

    private String quantity;

    private Boolean divisible = true;

    private String description;

    private boolean callable;

    private Date callDate;

    private String callPrice;

    private AssetCategory assetCategory;

    private AssetCategory customAssetCategory;

    private AssetCategory subAssetCategory;

    private AssetCategory customSubAssetCategory;

    private String password;

    public AssetCategory getCustomAssetCategory() {
        return customAssetCategory;
    }

    public void setCustomAssetCategory(AssetCategory customAssetCategory) {
        this.customAssetCategory = customAssetCategory;
    }

    public AssetCategory getCustomSubAssetCategory() {
        return customSubAssetCategory;
    }

    public void setCustomSubAssetCategory(AssetCategory customSubAssetCategory) {
        this.customSubAssetCategory = customSubAssetCategory;
    }

    private boolean locked;

    private boolean valid;

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public String getCallPrice() {
        return callPrice;
    }

    public void setCallPrice(String callPrice) {
        this.callPrice = callPrice;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Boolean getDivisible() {
        return divisible;
    }

    public void setDivisible(Boolean divisible) {
        this.divisible = divisible;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isCallable() {
        return this.callable;
    }

    public void setCallable(boolean callable) {
        this.callable = callable;
    }

    public AssetCategory getAssetCategory() {
        return assetCategory;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAssetCategory(AssetCategory assetCategory) {
        this.assetCategory = assetCategory;
    }

    public AssetCategory getSubAssetCategory() {
        return subAssetCategory;
    }

    public void setSubAssetCategory(AssetCategory subAssetCategory) {
        this.subAssetCategory = subAssetCategory;
    }

}
