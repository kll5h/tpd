package com.tilepay.counterpartyclient.service;

public class PayloadBuilder {

    private String method;
    private Params params;

    public PayloadBuilder setMethod(String method) {
        this.method = method;
        return this;
    }

    public PayloadBuilder setParams(Params params) {
        this.params = params;
        return this;
    }

    public static PayloadBuilder aPayload() {
        return new PayloadBuilder();
    }

    public Payload build() {
        Payload object = new Payload();
        object.setMethod(method);
        object.setParams(params);
        return object;
    }
}
