package com.tilepay.domain.entity;

import static javax.persistence.GenerationType.AUTO;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Asset {

    public static final BigDecimal BTC_MINERS_FEE = new BigDecimal("0.0001");
    public static final BigDecimal CUSTOM_CURRENCIES_MINERS_FEE = new BigDecimal("0.00001");

    public static final String CNTRPRTY_TILECOINX = "CNTRPRTY-TILECOINXTC";

    public static final Asset CNTRPRTY_TILECOINX_ASSET = AssetBuilder.anAsset().buildCntrprtyTilecoinx();

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String assetId;

    private String name;

    //TODO: 03.01.2015 Andrei Sljusar: to DB?
    @Transient
    private BigDecimal minersFee;

    @Transient
    private String protocol;

    private Boolean divisible;

    private Boolean booked;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMinersFee() {
        return minersFee;
    }

    public void setMinersFee(BigDecimal minersFee) {
        this.minersFee = minersFee;
    }

    public boolean isBTC() {
        return this.name.equals(CurrencyEnum.BTC.name());
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Asset other = (Asset) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    public boolean isCntrprty() {
        return getProtocol().equals(Protocol.CNTRPRTY.name());
    }

    public boolean isBitcoin() {
        return getProtocol().equals(Protocol.BITCOIN.name());
    }

    public boolean isTilecoin() {
        return getProtocol().equals(Protocol.TILECOIN.name());
    }

    public Boolean getDivisible() {
        return divisible;
    }

    public void setDivisible(Boolean divisible) {
        this.divisible = divisible;
    }

    public Boolean getBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    public boolean checkForDuplicateName(List<Asset> assets, String name) {
        Asset buf = null;
        for (Asset asset : assets) {
            if (asset.getName().equals(name))
                if (buf != null && buf.getName().equals(name)) {
                    return true;
                }
            buf = asset;
        }
        return false;
    }

    public void setTilepayProtocol() {
        this.protocol = Protocol.TILECOIN.name();
    }
}
