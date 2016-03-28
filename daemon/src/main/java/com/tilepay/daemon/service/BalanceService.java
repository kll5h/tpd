package com.tilepay.daemon.service;

import java.math.BigInteger;

import javax.inject.Inject;

import com.tilepay.daemon.persistence.BalanceRepository;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.LedgerEntry;

public abstract class BalanceService {

    @Inject
    protected BalanceRepository balanceRepository;

    //TODO: 03.01.2015 Andrei Sljusar: if quantity is 0/null -> ignore/exception?
    public LedgerEntry changeBalance(String address, Asset asset, BigInteger quantity) {
        if (isNegative(quantity)) {
            throw new NegativeQuantityException("Quantity: " + quantity + " must be positive.");
        }

        //TODO: 03.01.2015 Andrei Sljusar:
        /*if asset == config.BTC:
        raise CreditError*/

        Balance actualBalance = balanceRepository.findByAddressAndAssetName(address, asset.getName());

        if (actualBalance == null) {
            actualBalance = newBalance(address, asset, quantity);
        } else {
            updateBalance(asset, actualBalance, quantity);
        }

        balanceRepository.save(actualBalance);

        return insertLedgerEntry(address, asset, quantity);
    }

    protected abstract LedgerEntry insertLedgerEntry(String address, Asset asset, BigInteger quantity);

    public boolean isNegative(BigInteger b) {
        return b.signum() == -1;
    }

    protected abstract Balance newBalance(String address, Asset asset, BigInteger quantity);

    protected abstract void updateBalance(Asset asset, Balance actualBalance, BigInteger quantity);
}
