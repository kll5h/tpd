package com.tilepay.bitreserveclient.model.user;

import java.io.Serializable;

public class Intl implements Serializable {

    private static final long serialVersionUID = 2213574358155796634L;

    private DateTimeFormat dateTimeFormat;
    private Language language;
    private NumberFormat numberFormat;

    public DateTimeFormat getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(DateTimeFormat dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public NumberFormat getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }
}
