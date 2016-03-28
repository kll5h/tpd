package com.tilepay.domain.entity;

import static javax.persistence.GenerationType.AUTO;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Balance {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private String address;

    private BigInteger quantity;

    @Transient
    private BigInteger estimatedQuantity = new BigInteger("0");

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

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(BigInteger addQuantity) {
        this.quantity = this.quantity.add(addQuantity);
    }

    public void subtractQuantity(BigInteger subtractQuantity) {
        this.quantity = this.quantity.subtract(subtractQuantity);
    }

    public BigInteger getEstimatedQuantity() {
        return estimatedQuantity;
    }

    public void setEstimatedQuantity(BigInteger estimatedQuantity) {
        this.estimatedQuantity = estimatedQuantity;
    }

}
