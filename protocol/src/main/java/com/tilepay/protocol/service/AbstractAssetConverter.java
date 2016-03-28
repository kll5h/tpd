package com.tilepay.protocol.service;

import java.math.BigInteger;

public abstract class AbstractAssetConverter {

    //TODO: 18.10.2014 Andrei Sljusar: AssetEnum, id + name
    private static final String BTC = "BTC";
    private static final String XCP = "XCP";

    private static final BigInteger BTC_ID = BigInteger.ZERO;
    private static final BigInteger XCP_ID = BigInteger.ONE;

    private static final BigInteger min = new BigInteger("26").pow(3);

    public BigInteger getAssetId(String asset) {

        if (BTC.equals(asset)) {
            return BTC_ID;
        }

        if (XCP.equals(asset)) {
            return XCP_ID;
        }

        if (asset.length() < 4) {
            throw new IllegalArgumentException(asset + " should be at least 4 characters");
        }

        return generateAssetId(asset);
    }

    protected abstract BigInteger generateAssetId(String asset);

    public String getAssetName(BigInteger assetId) {

        if (assetId.equals(BTC_ID)) {
            return BTC;
        }

        if (assetId.equals(XCP_ID)) {
            return XCP;
        }

        if (assetId.compareTo(min) < 0) {
            throw new IllegalArgumentException(assetId + " is too short. Should be < 17576");
        }

        //TODO: 14.12.2014 Andrei Sljusar:
        /*else:
            raise exceptions.AssetIDError('too high')*/
        return generateAssetName(assetId);
    }

    protected abstract String generateAssetName(BigInteger assetId);
}
