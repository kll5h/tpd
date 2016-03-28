package com.tilepay.upholdclient.model.card;

import java.io.Serializable;

public class Normalized implements Serializable {

    private static final long serialVersionUID = -1023506767731540144L;

    private String available;
    private String balance;
    private String currency;

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
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

}
