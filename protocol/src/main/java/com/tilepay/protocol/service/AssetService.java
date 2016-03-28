package com.tilepay.protocol.service;

import java.math.BigInteger;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class AssetService {

    @Inject
    private NumericAssetConverter numericAssetConverter;

    @Inject
    private Base26AssetConverter base26AssetConverter;

    public BigInteger getAssetId(String asset) {
        return getConverter(asset).getAssetId(asset);
    }

    public String getAssetName(BigInteger assetId) {
        if (numericAssetConverter.isInRange(assetId)) {
            return numericAssetConverter.getAssetName(assetId);
        } else {
            return base26AssetConverter.getAssetName(assetId);
        }
    }

    //TODO: 14.12.2014 Andrei Sljusar: # Numeric asset names.
    //if asset_names_v2_enabled(block_index):  # Protocol change.
    //TODO: 14.12.2014 Andrei Sljusar: how to choose right converter?
    private AbstractAssetConverter getConverter(String asset) {
        if (asset.startsWith("A")) {
            return numericAssetConverter;
        } else {
            return base26AssetConverter;
        }
    }
}
