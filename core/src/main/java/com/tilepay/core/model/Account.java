package com.tilepay.core.model;

import java.io.Serializable;

import javax.persistence.*;

import static javax.persistence.InheritanceType.JOINED;

@Entity
@Inheritance(strategy = JOINED)
public class Account implements Serializable {

    private static final long serialVersionUID = -5049940348436090995L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // TODO: 23.09.2014 Andrei Sljusar: to move to wallet?
    // TODO: 23.09.2014 Andrei Sljusar: decrypt it
    private String passPhrase;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @Transient
    private String password;

    @Transient
    private AccountType accountType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
