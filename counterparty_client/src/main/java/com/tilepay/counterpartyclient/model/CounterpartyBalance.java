package com.tilepay.counterpartyclient.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CounterpartyBalance {

    /**
     * The address that has the balance
     */
    private String address;

    /**
     * The ID of the assets in which the balance is specified
     */
    private String asset;

    /**
     * The balance of the specified asset at this address
     */
    //private BigInteger quantity = BigInteger.ZERO;
    private BigDecimal quantity = BigDecimal.ZERO;

    private BigDecimal normalized_quantity;

    /**
     * The estimated balance of the specified asset at this address
     */
    private long estimatedQuantity;

    public CounterpartyBalance() {
    }

    public CounterpartyBalance(String address, String asset) {
        this.address = address;
        this.asset = asset;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    /*public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }*/

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public long getEstimatedQuantity() {
        return estimatedQuantity;
    }

    public void setEstimatedQuantity(long estimatedQuantity) {
        this.estimatedQuantity = estimatedQuantity;
    }

    public BigDecimal getNormalized_quantity() {
        return normalized_quantity;
    }

    public void setNormalized_quantity(BigDecimal normalized_quantity) {
        this.normalized_quantity = normalized_quantity;
    }

    @Override
    public String toString() {
        return "Balance [address=" + address + ", asset=" + asset + ", quantity=" + quantity + "]";
    }

    public BigInteger getQuantityAsBigInteger() {
        return this.quantity.toBigInteger();
    }
}
