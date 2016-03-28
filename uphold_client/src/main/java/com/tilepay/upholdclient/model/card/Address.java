package com.tilepay.upholdclient.model.card;

import java.io.Serializable;

public class Address implements Serializable {

    private static final long serialVersionUID = -5406866557388264372L;

    private String bitcoin;

    public String getBitcoin() {
        return bitcoin;
    }

    public void setBitcoin(String bitcoin) {
        this.bitcoin = bitcoin;
    }

}
