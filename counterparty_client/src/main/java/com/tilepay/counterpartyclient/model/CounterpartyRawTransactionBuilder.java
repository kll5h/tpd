package com.tilepay.counterpartyclient.model;

public class CounterpartyRawTransactionBuilder {

    private String tx_hash;
    private String source;

    public static CounterpartyRawTransactionBuilder aCounterpartyRawTransaction() {
        return new CounterpartyRawTransactionBuilder();
    }

    public CounterpartyRawTransactionBuilder withTxHash(String tx_hash) {
        this.tx_hash = tx_hash;
        return this;
    }

    public CounterpartyRawTransactionBuilder withSource(String source) {
        this.source = source;
        return this;
    }

    public CounterpartyRawTransaction build() {
        CounterpartyRawTransaction counterpartyRawTransaction = new CounterpartyRawTransaction();
        counterpartyRawTransaction.setTx_hash(tx_hash);
        counterpartyRawTransaction.setSource(source);
        return counterpartyRawTransaction;
    }
}
