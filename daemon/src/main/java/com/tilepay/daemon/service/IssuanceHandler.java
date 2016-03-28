package com.tilepay.daemon.service;

import org.springframework.stereotype.Service;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.IssuanceStatus;
import com.tilepay.domain.entity.Transaction;

@Service
public class IssuanceHandler extends TransactionHandler<Issuance> {

    public void handle(Transaction tx, Issuance issuance) {

        Asset asset = issuance.getAsset();
        issuance.setAsset(null);

        Asset currentAsset = assetRepository.findByName(asset.getName());

        if (currentAsset == null) {
            asset.setBooked(true);
            assetRepository.save(asset);

            issuance.setStatus(IssuanceStatus.FEE_IS_NOT_CHARGED);
            issuance.setAsset(asset);
            messageRepository.save(issuance);
        } else {
            if (currentAsset.getBooked()) {
                Issuance currentIssuance = messageRepository.findIssuanceByAssetName(currentAsset.getName());
                if (currentIssuance == null) {
                    issuance.setStatus(IssuanceStatus.FEE_IS_NOT_CHARGED);
                    issuance.setAsset(currentAsset);
                    messageRepository.save(issuance);
                } else {
                    if (currentIssuance.getStatus() == IssuanceStatus.CREATED) {
                        throw new IssuedByAnotherAddressException("Asset: " + currentAsset.getName() + " issued by another address: " /* + currentIssuance.getTransaction().getSource()*/);
                    }
                }
            }

        }
    }

    @Override
    public void handle(Transaction tx) {
        handle(tx, (Issuance) tx.getMessage());
    }
}
