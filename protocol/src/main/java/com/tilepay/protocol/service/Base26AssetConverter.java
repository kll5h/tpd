package com.tilepay.protocol.service;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

@Component
public class Base26AssetConverter extends AbstractAssetConverter {

    private static final String B26_DIGITS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    protected BigInteger generateAssetId(String asset) {

        if (asset.startsWith("A")) {
            throw new IllegalArgumentException(asset + " should not start with 'A'");
        }

        if (asset.length() >= 13) {
            throw new IllegalArgumentException("Base 26 asset name max length: 12. " + asset + " is " + asset.length() + ".");
        }

        long assetId = 0;

        for (int i = 0; i < asset.length(); i++) {
            assetId *= 26;
            char c = asset.charAt(i);
            if (!B26_DIGITS.contains(Character.toString(c))) {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
            int digit = B26_DIGITS.indexOf(c);
            assetId += digit;
        }

        if (assetId < Math.pow(26, 3)) {
            throw new IllegalArgumentException(assetId + " is too short. Should be < 17576");
        }

        return BigInteger.valueOf(assetId);
    }

    @Override
    protected String generateAssetName(BigInteger assetId) {
        long n = assetId.longValue();
        String assetName = "";

        while (n > 0) {
            long r = n % 26;
            n = n / 26;
            char c = B26_DIGITS.charAt((int) r);
            assetName = c + assetName;
        }

        if (assetName.isEmpty()) {
            throw new IllegalArgumentException("Can't generate asset name from asset id: " + assetId);
        }

        if (assetId.longValue() < Math.pow(26, 3)) {
            throw new IllegalArgumentException(assetId + " is too short. Should be < 17576");
        }

        return assetName;

    }
}
