package com.tilepay.upholdclient.model.user;

import java.io.Serializable;

public class Language implements Serializable {

    private static final long serialVersionUID = -7010350580542636277L;

    private String locale;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

}
