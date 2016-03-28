package com.tilepay.domain.entity;

import static javax.persistence.InheritanceType.JOINED;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = JOINED)
public class Message {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @OneToOne(mappedBy = "message")
    private Transaction transaction;

    @Transient
    private byte[] data;

    //TODO: 31.12.2014 Andrei Sljusar: save to db?
    @Transient
    private BigInteger quantity;

    //TODO: 04.01.2015 Andrei Sljusar: ?
    @Transient
    private Integer length;

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public String getAssetName() {
        return this.asset.getName();
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
