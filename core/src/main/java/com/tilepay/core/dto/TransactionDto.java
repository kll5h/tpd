package com.tilepay.core.dto;

import static com.tilepay.core.constant.RegExpPattern.DOUBLE_NUMBER;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.tilepay.domain.entity.Asset;

public class TransactionDto {

    @NotBlank
    private String addressFrom;

    @NotBlank
    private String addressTo;

    @NotBlank
    @Pattern(regexp = DOUBLE_NUMBER)
    private String amount;

    @Pattern(regexp = DOUBLE_NUMBER)
    private String minersFee;

    @NotNull
    private Asset asset = new Asset();

    private String password;

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMinersFee() {
        return minersFee;
    }

    public void setMinersFee(String minersFee) {
        this.minersFee = minersFee;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getAmountAsBigDecimal() {
        return isBlank(amount) ? null : new BigDecimal(amount);
    }

    public BigDecimal getMinersFeeAsBigDecimal() {
        return isBlank(minersFee) ? null : new BigDecimal(minersFee);
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

}
