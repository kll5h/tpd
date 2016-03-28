package com.tilepay.domain.entity;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//TODO: 22.12.2014 Andrei Sljusar: needed?
//@Entity
public class Burn {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    private String source;

    private Long burned;

    private Long earned;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getBurned() {
        return burned;
    }

    public void setBurned(Long burned) {
        this.burned = burned;
    }

    public Long getEarned() {
        return earned;
    }

    public void setEarned(Long earned) {
        this.earned = earned;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
