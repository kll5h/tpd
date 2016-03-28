package com.tilepay.domain.entity;

public enum Protocol {
    TILECOIN, CNTRPRTY, BITCOIN, IOTC;

    public byte[] getBytes() {
        return name().getBytes();
    }
}
