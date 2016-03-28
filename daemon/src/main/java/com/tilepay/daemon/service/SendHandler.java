package com.tilepay.daemon.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.LedgerEntry;
import com.tilepay.domain.entity.Send;
import com.tilepay.domain.entity.Transaction;

@Service
public class SendHandler extends TransactionHandler<Send> {

    public void handle(Transaction tx, Send send) {
        String assetName = send.getAssetName();
        send.setAsset(null);
        Asset asset = assetRepository.findByName(assetName);

        if (asset == null) {
            throw new AssetDoesNotExistException(assetName + " does not exist");
        }

        send.setAsset(asset);

        //TODO: 03.01.2015 Andrei Sljusar:
        /*
        if status == 'valid':
            # For SQLite3
            quantity = min(quantity, config.MAX_INT)
            problems = validate(db, tx['source'], tx['destination'], asset, quantity, tx['block_index'])
            if problems: status = 'invalid: ' + '; '.join(problems)*/

        BigInteger quantity = send.getQuantity();

        LedgerEntry debit = debitService.changeBalance(tx.getSource(), asset, quantity);
        LedgerEntry credit = creditService.changeBalance(tx.getDestination(), asset, quantity);

        tx.addLedgerEntry(credit);
        tx.addLedgerEntry(debit);

        messageRepository.save(send);
    }

    @Override
    public void handle(Transaction tx) {
        handle(tx, (Send) tx.getMessage());
    }

}
