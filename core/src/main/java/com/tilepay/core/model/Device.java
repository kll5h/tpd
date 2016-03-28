package com.tilepay.core.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="REGISTERED_DEVICE")
public class Device implements Serializable {

    private static final long serialVersionUID = -2114778922566661968L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private String address;

    private String registrationToken;
    
    private String name;
    
    private String IPAddress;
    
    private String description;

    @Column(name= "txhash")
    private String txHash;
    
    @ManyToOne(cascade = ALL, fetch = EAGER)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
    
    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String iPAddress) {
        IPAddress = iPAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
    
}
