package com.tilepay.upholdclient.model.transaction;

import java.io.Serializable;

public class Source implements Serializable {

    private static final long serialVersionUID = 3412414162725731091L;

    private String id;
    private String amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
