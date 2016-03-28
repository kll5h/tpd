package com.tilepay.upholdclient.model.transaction;

import java.io.Serializable;

public class Normalized implements Serializable {

    private static final long serialVersionUID = 560641371799990L;

    private String amount;
    private String commission;
    private String currency;
    private String rate;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
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
