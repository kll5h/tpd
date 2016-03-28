package com.tilepay.core.dto;

import java.math.BigDecimal;

public class WalletDTO {

    private String receiveAddress;
    private String balanceAvailable;
    private String balanceEstimated;
    private String privateKey;
    private String passPhrase;

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getBalanceAvailable() {
        return balanceAvailable;
    }

    public void setBalanceAvailable(String balanceAvailable) {
        this.balanceAvailable = balanceAvailable;
    }

    public String getBalanceEstimated() {
        return balanceEstimated;
    }

    public void setBalanceEstimated(String balanceEstimated) {
        this.balanceEstimated = balanceEstimated;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }

    public BigDecimal getBtcAvailableBalanceAsBigDecimal() {
        return new BigDecimal(balanceAvailable);
    }
}
