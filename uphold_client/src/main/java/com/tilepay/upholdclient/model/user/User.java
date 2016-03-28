package com.tilepay.upholdclient.model.user;

import java.io.Serializable;
import java.util.List;

import com.tilepay.upholdclient.model.card.Card;

public class User implements Serializable {

    private static final long serialVersionUID = 3254599666629217004L;
    private String memberAt;
    private Address address;
    private String birthdate;
    private String country;
    private List<String> currencies;
    private String email;
    private String firstName;
    private Object identity;
    private String lastName;
    private String name;
    private List<Phone> phones;
    private Settings settings;
    private String state;
    private String status;
    private String username;
    private Verifications verifications;
    private Balances balances;
    private List<Card> cards;

    public String getMemberAt() {
        return memberAt;
    }

    public void setMemberAt(String memberAt) {
        this.memberAt = memberAt;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Object getIdentity() {
        return identity;
    }

    public void setIdentity(Object identity) {
        this.identity = identity;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Verifications getVerifications() {
        return verifications;
    }

    public void setVerifications(Verifications verifications) {
        this.verifications = verifications;
    }

    public Balances getBalances() {
        return balances;
    }

    public void setBalances(Balances balances) {
        this.balances = balances;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

}
