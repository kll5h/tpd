package com.tilepay.counterpartyclient.model;

import java.math.BigDecimal;
import java.util.Date;

public class IssuanceInfo {
    
    private String txHash;//"tx_hash": "ee064c57d12ad5654f7f0a18a5406ad5f3011196ef7d8bb0186d611a9eca7f24", 
    private String status;//"status": "valid", 
    private Date callDate;//"call_date": 0, 
    private Integer locked;//"locked": 0, 
    private String description;//"description": "", 
    private Long blockIndex;//"block_index": 280332, 
    private Integer divisible;//"divisible": 0, 
    private Integer transfer;//"transfer": 0, 
    private Long txIndex;//"tx_index": 869, 
    private Integer callable;//"callable": 0, 
    private String source;//"source": "1Fz5idgemYfsszRMmaQNd2Anvm2JytAoU", 
    private String asset;//"asset": "TEST", 
    private String issuer;//"issuer": "1Fz5idgemYfsszRMmaQNd2Anvm2JytAoU", 
    private BigDecimal callPrice;//"call_price": 0, 
    private BigDecimal feePaid;//"fee_paid": 0, 
    private BigDecimal quantity = BigDecimal.ZERO;//"quantity": 1000
    
    public String getTxHash() {
        return txHash;
    }
    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getCallDate() {
        return callDate;
    }
    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }
    public Integer getLocked() {
        return locked;
    }
    public void setLocked(Integer locked) {
        this.locked = locked;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getBlockIndex() {
        return blockIndex;
    }
    public void setBlockIndex(Long blockIndex) {
        this.blockIndex = blockIndex;
    }
    public Integer getDivisible() {
        return divisible;
    }
    public void setDivisible(Integer divisible) {
        this.divisible = divisible;
    }
    public Integer getTransfer() {
        return transfer;
    }
    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
    }
    public Long getTxIndex() {
        return txIndex;
    }
    public void setTxIndex(Long txIndex) {
        this.txIndex = txIndex;
    }
    public Integer getCallable() {
        return callable;
    }
    public void setCallable(Integer callable) {
        this.callable = callable;
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
    public String getIssuer() {
        return issuer;
    }
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
    public BigDecimal getCallPrice() {
        return callPrice;
    }
    public void setCallPrice(BigDecimal callPrice) {
        this.callPrice = callPrice;
    }
    public BigDecimal getFeePaid() {
        return feePaid;
    }
    public void setFeePaid(BigDecimal feePaid) {
        this.feePaid = feePaid;
    }
    public BigDecimal getQuantity() {
        return quantity;
    }
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    
    
}
