package com.tilepay.bitreserveclient.model.user;

import java.io.Serializable;

public class NumberFormat implements Serializable {

    private static final long serialVersionUID = -4720125713722654135L;

    private String locale;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

}
