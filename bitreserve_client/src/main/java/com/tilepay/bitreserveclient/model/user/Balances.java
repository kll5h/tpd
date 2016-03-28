package com.tilepay.bitreserveclient.model.user;

import java.io.Serializable;

import com.tilepay.bitreserveclient.model.currency.Currencies;

public class Balances implements Serializable {

    private static final long serialVersionUID = -1677790607663801741L;

    private String total;
    private Currencies currencies;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Currencies getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Currencies currencies) {
        this.currencies = currencies;
    }

}
