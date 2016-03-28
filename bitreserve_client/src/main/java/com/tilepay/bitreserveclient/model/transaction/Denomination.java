package com.tilepay.bitreserveclient.model.transaction;

import java.io.Serializable;

public class Denomination implements Serializable {

    private static final long serialVersionUID = 8202715044864051311L;

    private String amount;
    private String currency;
    private String pair;
    private String rate;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

}
