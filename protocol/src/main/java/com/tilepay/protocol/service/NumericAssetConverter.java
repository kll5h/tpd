package com.tilepay.protocol.service;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

@Component
public class NumericAssetConverter extends AbstractAssetConverter {

    private static final BigInteger min = new BigInteger("26").pow(12).add(new BigInteger("1"));
    private static final BigInteger max = new BigInteger("2").pow(64).subtract(new BigInteger("1"));

    @Override
    protected BigInteger generateAssetId(String assetName) {
        if (assetName.startsWith("A")) {
            BigInteger assetId;
            try {
                assetId = new BigInteger(assetName.substring(1));
            } catch (NumberFormatException e) {
                //TODO: 25.12.2014 Andrei Sljusar: AssetException
                throw new IllegalArgumentException("Numeric asset name: " + assetName + " should start with 'A' followed by numbers");
            }
            return toAssetName(assetId);
        } else {
            throw new IllegalArgumentException("Numeric asset name: " + assetName + " should start with 'A'");
        }
    }

    @Override
    protected String generateAssetName(BigInteger assetId) {
        return "A" + toAssetName(assetId);
    }

    public BigInteger toAssetName(BigInteger assetId) {
        if (isInRange(assetId)) {
            return assetId;
        } else {
            throw new IllegalArgumentException("Numeric asset id: " + assetId + " should be in range: [" + min + ", " + max + "]");
        }
    }

    public boolean isInRange(BigInteger assetId) {
        return (min.compareTo(assetId) <= 0 && assetId.compareTo(max) <= 0);
    }

}
