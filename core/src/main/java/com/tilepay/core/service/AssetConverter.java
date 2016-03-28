package com.tilepay.core.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.tilepay.core.dto.AssetIssuanceDto;
import com.tilepay.counterpartyclient.model.AssetInfo;
import com.tilepay.counterpartyclient.service.CounterpartyService;
import com.tilepay.domain.entity.Asset;

@Component
public class AssetConverter {

    @Inject
    private CounterpartyService counterpartyService;

    public Asset getAsset(String assetName) {
        // TODO: Jan 5, 2015 Mait Mikkelsaar: make one call for all assets instead of each asset?
        AssetInfo assetInfo = getAssetInfo(assetName);
        if (assetInfo==null) {
            return null;
        }
        return getAsset(assetInfo);
    }
    
    public List<Asset> getAssets(String... assetNames) {
    	List<Asset> assets = new ArrayList<Asset>();
        List<AssetInfo> assetInfos = getAssetInfos(assetNames);
        for (AssetInfo assetInfo : assetInfos){
        	assets.add(getAsset(assetInfo));
        }
        return assets;
    }

    public Asset getAsset(AssetInfo assetInfo) {
        return anAsset().setName(assetInfo.getAsset()).setCntrprtyProtocol().setDivisible(assetInfo.getDivisible()).build();
    }

    public void setAssetDivisibility(Asset asset) {
        AssetInfo assetInfo = getAssetInfo(asset.getName());
        if (assetInfo != null) {
            asset.setDivisible(assetInfo.getDivisible());
        }
    }

    public Asset getAsset(AssetIssuanceDto form) {
        return anAsset().setName(form.getAssetName()).setDivisible(form.getDivisible()).setTilecoinProtocol().build();
    }

    private AssetInfo getAssetInfo(String assetName) {
        return counterpartyService.getOneAssetInfo(assetName);
    }
    
    private List<AssetInfo> getAssetInfos(String... assetNames) {
        return counterpartyService.getAssetInfos(assetNames);
    }
}
