package com.tilepay.domain.entity;

import static javax.persistence.GenerationType.AUTO;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Block {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private Integer index;
    private String hash;
    private Long time;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "block", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setBlock(this);
    }

    //TODO: 27.11.2014 Andrei Sljusar: equals and hashCode
}
