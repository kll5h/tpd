package com.tilepay.daemon.service;

import org.springframework.stereotype.Service;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.LedgerEntry;
import com.tilepay.domain.entity.Transaction;

@Service
public class _IssuanceHandler extends TransactionHandler<Issuance> {

    //TODO: 20.01.2015 Andrei Sljusar: save issuance with status: no fee charged
    //TODO: 20.01.2015 Andrei Sljusar: task/thread: get debits of source from counterparty. if ok -> create asset

    public void handle(Transaction tx, Issuance issuance) {
        Asset asset = issuance.getAsset();
        issuance.setAsset(null);
        //Asset currentAsset = daemonAssetService.findByName(asset);
        Asset currentAsset = null;

        if (currentAsset == null) {
            asset.setBooked(true);
            assetRepository.save(asset);
        } else {
            if (currentAsset.getBooked()) {
                Issuance currentIssuance = messageRepository.findIssuanceByAssetName(currentAsset.getName());
                if (currentIssuance == null) {
                    asset = currentAsset;
                } else {
                    return;
                }
            }
        }

        /*if last_issuance['issuer'] != source:
            problems.append('issued by another address')*/

        /* if last_issuance['locked'] and quantity:
            problems.append('locked asset and non‐zero quantity')*/

        /*if description.lower() == 'lock':
            problems.append('cannot lock a non‐existent asset')*/

        /* if destination:
            problems.append('cannot transfer a non‐existent asset')*/

        /*if destination and quantity:
        problems.append('cannot issue and transfer simultaneously')

        if total + quantity > config.MAX_INT:
        problems.append('total quantity overflow')
        */
        issuance.setAsset(asset);
        messageRepository.save(issuance);

        //TODO: 03.01.2015 Andrei Sljusar:
        /*# Debit fee.
            if status == 'valid':
            util.debit(db, tx['block_index'], tx['source'], config.XCP, fee, action="issuance fee", event=tx['tx_hash'])*/

        //TODO: 05.01.2015 Andrei Sljusar:
        /* if status == 'valid' and quantity:*/
        LedgerEntry ledgerEntry = creditService.changeBalance(tx.getSource(), asset, issuance.getQuantity());
        tx.addLedgerEntry(ledgerEntry);
    }

    @Override
    public void handle(Transaction tx) {
        handle(tx, (Issuance) tx.getMessage());
    }
}
