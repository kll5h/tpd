package com.tilepay.daemon.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.daemon.persistence.AssetRepository;
import com.tilepay.domain.entity.Asset;

@Service
public class DaemonAssetService {

    @Inject
    private AssetRepository assetRepository;

    //TODO: 22.01.2015 Andrei Sljusar: org.springframework.transaction.IllegalTransactionStateException: Existing transaction found for transaction marked with propagation 'never'
    //@Transactional(value = Transactional.TxType.NEVER)
    /*public Asset findByName(Asset asset) {
        return assetRepository.findByName(asset.getName());
    }*/

    public void saveAsset(Asset asset) {
        Asset currentAsset = assetRepository.findByName(asset.getName());
        if (currentAsset == null) {
            asset.setBooked(true);
            assetRepository.save(asset);
        }
        //TODO: 06.01.2015 Andrei Sljusar: exists and taken/not taken?
    }

    public Asset getCntrprtyTilecoinxAsset() {
        Asset asset = Asset.CNTRPRTY_TILECOINX_ASSET;
        Asset currentAsset = assetRepository.findByName(asset.getName());
        if (currentAsset == null) {
            assetRepository.save(asset);
        } else {
            asset = currentAsset;
        }
        return asset;
    }
}
