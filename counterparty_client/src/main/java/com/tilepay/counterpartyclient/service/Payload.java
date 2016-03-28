package com.tilepay.counterpartyclient.service;

public class Payload {
    private String method;
    private Integer id = 0;
    private String jsonrpc = "2.0";
    private Params params;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }
}
