package com.tilepay.counterpartyclient.model;

public class CounterpartyStatusBuilder {

    private String counterpartyd;

    private CounterpartyStatus object = new CounterpartyStatus();

    public static CounterpartyStatusBuilder aCounterpartyStatus() {
        return new CounterpartyStatusBuilder();
    }

    public CounterpartyStatusBuilder setCounterpartyd(String counterpartyd) {
        this.counterpartyd = counterpartyd;
        return this;
    }

    public CounterpartyStatus build() {
        object.setCounterpartyd(counterpartyd);
        return object;
    }

}