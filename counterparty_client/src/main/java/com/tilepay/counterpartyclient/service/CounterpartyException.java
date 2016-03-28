package com.tilepay.counterpartyclient.service;

import com.google.gson.Gson;

public abstract class CounterpartyException extends RuntimeException {

    private static final long serialVersionUID = 7093670485959995965L;

    protected static final Gson gson = new Gson();

    public CounterpartyException(String s) {
        super(s);
    }

}