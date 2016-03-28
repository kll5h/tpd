package com.tilepay.bitreserveclient.model.transaction;

import java.io.Serializable;

public class Params implements Serializable {

    private static final long serialVersionUID = 4184131158792053054L;

    private String currency;
    private String margin;
    private String pair;
    private String progress;
    private String rate;
    private String ttl;
    private String type;
    private String refunds;
    
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefunds() {
        return refunds;
    }

    public void setRefunds(String refunds) {
        this.refunds = refunds;
    }

}
