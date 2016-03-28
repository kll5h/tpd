package com.tilepay.domain.entity;

import java.math.BigInteger;

public class LedgerEntryBuilder implements Builder<LedgerEntry> {

    private LedgerEntry type;

    private Asset asset;
    private String address;
    private BigInteger quantity;

    public static LedgerEntryBuilder aLedgerEntry() {
        return new LedgerEntryBuilder();
    }

    public LedgerEntryBuilder setType(LedgerEntry type) {
        this.type = type;
        return this;
    }

    public LedgerEntryBuilder setAsset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public LedgerEntryBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public LedgerEntryBuilder setQuantity(BigInteger quantity) {
        this.quantity = quantity;
        return this;
    }

    public LedgerEntryBuilder setQuantity(Integer quantity) {
        return setQuantity(BigInteger.valueOf(quantity));
    }

    @Override
    public LedgerEntry build() {
        LedgerEntry ledgerEntry;
        try {
            ledgerEntry = type.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ledgerEntry.setAddress(address);
        ledgerEntry.setQuantity(quantity);
        ledgerEntry.setAsset(asset);
        return ledgerEntry;
    }

    public Debit buildDebit() {
        return (Debit) setType(new Debit()).build();
    }

    public Credit buildCredit() {
        return (Credit) setType(new Credit()).build();
    }
}
