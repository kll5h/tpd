package com.tilepay.core.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.core.service.balance.TilecoinRestClient;
import com.tilepay.domain.entity.Asset;

@Service
public class AssetQuantityConversionService {

    @Inject
    private AssetConverter assetConverter;

    @Inject
    private TilecoinRestClient tilecoinRestClient;

    public static BigDecimal DECIMAL_PLACES = new BigDecimal(100_000_000);

    public BigDecimal format(Asset asset, BigInteger quantity) {
        Boolean divisible = asset.getDivisible();

        if (divisible == null) {
            throw new IllegalArgumentException("Divisible must be defined for: " + asset.getName());
        }

        return divisible ? new BigDecimal(quantity).divide(DECIMAL_PLACES) : new BigDecimal(quantity);
    }

    public String formatAsString(Asset asset, BigInteger quantity) {
        return format(asset, quantity).toPlainString();
    }

    public BigInteger formatAsBigInteger(Asset asset, String quantity) {
        Boolean divisible = asset.getDivisible();

        if (divisible == null) {
            throw new IllegalArgumentException("Divisible must be defined for: " + asset.getName());
        }

        return divisible ? new BigDecimal(quantity).multiply(DECIMAL_PLACES).toBigInteger() : new BigInteger(quantity);
    }

    public BigDecimal getMultipliedBigDecimal(BigDecimal bigDecimal) {
        return bigDecimal.multiply(DECIMAL_PLACES);
    }

    public BigInteger getMultipliedBigInteger(String s) {
        return getMultipliedBigDecimal(new BigDecimal(s)).toBigInteger();
    }

    public BigInteger getQuantity(Asset asset, String quantityAsString) {
        if (asset.isCntrprty()) {
            asset = assetConverter.getAsset(asset.getName());
        } else {
            asset = tilecoinRestClient.getAssetByName(asset.getName());
        }
        return formatAsBigInteger(asset, quantityAsString);
    }
    
    public BigInteger getTilecoinQuantity(Asset asset, BigInteger quantity) {
        Boolean divisible = asset.getDivisible();

        if (divisible == null) {
            throw new IllegalArgumentException("Divisible must be defined for: " + asset.getName());
        }

        return divisible ? new BigDecimal(quantity).multiply(DECIMAL_PLACES).toBigInteger() : quantity;
    }
}
