package com.tilepay.daemon.service;

import static com.tilepay.domain.entity.BalanceBuilder.aBalance;
import static com.tilepay.domain.entity.LedgerEntryBuilder.aLedgerEntry;

import java.math.BigInteger;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.daemon.persistence.DebitRepository;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.Debit;
import com.tilepay.domain.entity.LedgerEntry;

//-
@Service
public class DebitService extends BalanceService {

    @Inject
    private DebitRepository debitRepository;

    @Override
    protected void updateBalance(Asset asset, Balance actualBalance, BigInteger quantity) {

        if (asset.getName().equals(Asset.CNTRPRTY_TILECOINX)) {
            actualBalance.addQuantity(quantity);
            return;
        }

        //TODO: 13.01.2015 Andrei Sljusar: divisibility/indivisibility
        if (actualBalance.getQuantity().compareTo(quantity) == -1) {
            throw new InsufficientFundsException("Can't debit " + quantity + ". Available balance: " + actualBalance.getQuantity());
        }
        //TODO: 03.01.2015 Andrei Sljusar:
        /*if asset == config.BTC:
        raise exceptions.BalanceError('Cannot debit bitcoins from a {} address!'.format(config.XCP_NAME))*/

        //TODO: 03.01.2015 Andrei Sljusar:
        /*balance = round(old_balance - quantity)
        balance = min(balance, config.MAX_INT)
        assert balance >= 0*/
        actualBalance.subtractQuantity(quantity);
    }

    @Override
    protected LedgerEntry insertLedgerEntry(String address, Asset asset, BigInteger quantity) {
        Debit debit = aLedgerEntry().setAddress(address).setQuantity(quantity).setAsset(asset).buildDebit();
        debitRepository.save(debit);
        return debit;
    }

    @Override
    protected Balance newBalance(String address, Asset asset, BigInteger quantity) {
        if (asset.getName().equals(Asset.CNTRPRTY_TILECOINX)) {
            return aBalance().setAddress(address).setAsset(asset).setQuantity(quantity).build();
        }
        throw new InsufficientFundsException("Can't debit " + quantity + " " + asset.getName() + " from 0 balance. Address: " + address);
    }
}
