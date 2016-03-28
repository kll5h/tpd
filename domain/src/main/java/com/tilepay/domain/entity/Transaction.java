package com.tilepay.domain.entity;

import static javax.persistence.GenerationType.AUTO;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "block_id")
    private Block block;

    private String hash;

    private String source;
    private String destination;
    private Long btcAmount;
    private Long fee;
    private byte[] data;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_id")
    private Message message;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction", fetch = FetchType.EAGER)
    private Set<LedgerEntry> ledgerEntries = new HashSet<>();

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Long getBtcAmount() {
        return btcAmount;
    }

    public void setBtcAmount(Long btcAmount) {
        this.btcAmount = btcAmount;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
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

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Set<LedgerEntry> getLedgerEntries() {
        return ledgerEntries;
    }

    public void addLedgerEntry(LedgerEntry ledgerEntry) {
        this.ledgerEntries.add(ledgerEntry);
        ledgerEntry.setTransaction(this);
    }

}
