package com.tilepay.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

//TODO: 10.12.2014 Andrei Sljusar: length
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Issuance extends Message {

    @Transient
    public static Integer messageId = 20;

    @Column(name = "issuance_status_id")
    @Enumerated
    private IssuanceStatus status;

    public IssuanceStatus getStatus() {
        return status;
    }

    public void setStatus(IssuanceStatus status) {
        this.status = status;
    }
}
