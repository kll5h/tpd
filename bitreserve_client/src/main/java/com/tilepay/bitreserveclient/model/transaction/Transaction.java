package com.tilepay.bitreserveclient.model.transaction;

import java.io.Serializable;
import java.util.List;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 640753270413140696L;

    private String id;
    private String type;
    private String message;
    private Denomination denomination;
    private String status;
    private Params params;
    private String createdAt;
    private List<Normalized> normalized;
    private Origin origin;
    private Destination destination;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Normalized> getNormalized() {
        return normalized;
    }

    public void setNormalized(List<Normalized> normalized) {
        this.normalized = normalized;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

}
