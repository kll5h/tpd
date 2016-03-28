package com.tilepay.upholdclient.model.user;

import java.io.Serializable;

public class Settings implements Serializable {

    private static final long serialVersionUID = -8064830074022530546L;

    private String currency;
    private Boolean hasNewsSubscription;
    private Boolean hasOtpEnabled;
    private Intl intl;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getHasNewsSubscription() {
        return hasNewsSubscription;
    }

    public void setHasNewsSubscription(Boolean hasNewsSubscription) {
        this.hasNewsSubscription = hasNewsSubscription;
    }

    public Boolean getHasOtpEnabled() {
        return hasOtpEnabled;
    }

    public void setHasOtpEnabled(Boolean hasOtpEnabled) {
        this.hasOtpEnabled = hasOtpEnabled;
    }

    public Intl getIntl() {
        return intl;
    }

    public void setIntl(Intl intl) {
        this.intl = intl;
    }

}
