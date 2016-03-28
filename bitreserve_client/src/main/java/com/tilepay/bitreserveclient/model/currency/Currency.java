package com.tilepay.bitreserveclient.model.currency;

import java.io.Serializable;

public class Currency implements Serializable {

    private static final long serialVersionUID = 7343937524770221153L;

    private String amount;
    private String balance;
    private String currency;
    private String rate;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

}
