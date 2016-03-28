package com.tilepay.bitreserveclient.model.user;

import java.io.Serializable;

public class Identity implements Serializable {

    private static final long serialVersionUID = 2566536456378842025L;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
