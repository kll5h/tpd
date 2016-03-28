package com.tilepay.bitreserveclient.model.card;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Card implements Serializable {

    private static final long serialVersionUID = 3631209344593929503L;

    private String id;

    private Address address;

    private String available;

    private String balance;

    @NotBlank
    private String currency;

    @NotBlank
    @Size(min = 1, max = 140)
    private String label;

    private String lastTransactionAt;

    private Settings settings;

    private List<CardAddress> addresses;

    private List<Normalized> normalized;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLastTransactionAt() {
        return lastTransactionAt;
    }

    public void setLastTransactionAt(String lastTransactionAt) {
        this.lastTransactionAt = lastTransactionAt;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public List<CardAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<CardAddress> addresses) {
        this.addresses = addresses;
    }

    public List<Normalized> getNormalized() {
        return normalized;
    }

    public void setNormalized(List<Normalized> normalized) {
        this.normalized = normalized;
    }

}
