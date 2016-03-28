package com.tilepay.counterpartyclient.model;

public class CounterpartyStatus {

    private String counterpartyd;

    public String getCounterpartyd() {
        return counterpartyd;
    }

    public void setCounterpartyd(String counterpartyd) {
        this.counterpartyd = counterpartyd;
    }

    @Override
    public String toString() {
        return "CounterpartyStatus{" +
                "counterpartyd='" + counterpartyd + '\'' +
                '}';
    }
}
