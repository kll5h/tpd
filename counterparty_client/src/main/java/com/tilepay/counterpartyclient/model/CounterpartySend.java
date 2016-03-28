package com.tilepay.counterpartyclient.model;

import java.math.BigDecimal;

public class CounterpartySend {

    //private BigInteger quantity;
    private BigDecimal quantity;

    private String destination;
    private String tx_hash;
    private Integer block_index;
    private String source;
    private String asset;
    private String status;

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTx_hash() {
        return tx_hash;
    }

    public void setTx_hash(String tx_hash) {
        this.tx_hash = tx_hash;
    }

    public Integer getBlock_index() {
        return block_index;
    }

    public void setBlock_index(Integer block_index) {
        this.block_index = block_index;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
