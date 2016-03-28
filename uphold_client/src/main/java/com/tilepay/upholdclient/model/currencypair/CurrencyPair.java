package com.tilepay.upholdclient.model.currencypair;

import java.io.Serializable;

public class CurrencyPair implements Serializable {

    private static final long serialVersionUID = -5333461864893098190L;

    private String ask;
    private String bid;
    private String currency;
    private String pair;

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
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

}
