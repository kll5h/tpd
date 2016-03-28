package com.tilepay.bitreserveclient.model.user;

import java.io.Serializable;

public class DateTimeFormat implements Serializable {

    private static final long serialVersionUID = -2526557630816361680L;

    private String locale;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

}
