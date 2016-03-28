package com.tilepay.daemon.service;

import static com.tilepay.domain.entity.BalanceBuilder.aBalance;
import static com.tilepay.domain.entity.LedgerEntryBuilder.aLedgerEntry;

import java.math.BigInteger;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.daemon.persistence.CreditRepository;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.Credit;
import com.tilepay.domain.entity.LedgerEntry;

//+
@Service
public class CreditService extends BalanceService {

    @Inject
    private CreditRepository creditRepository;

    @Override
    protected void updateBalance(Asset asset, Balance actualBalance, BigInteger quantity) {
        //TODO: 03.01.2015 Andrei Sljusar:
        /*balance = round(old_balance + quantity)
        balance = min(balance, config.MAX_INT)*/
        actualBalance.addQuantity(quantity);
    }

    @Override
    protected LedgerEntry insertLedgerEntry(String address, Asset asset, BigInteger quantity) {
        Credit credit = aLedgerEntry().setAddress(address).setQuantity(quantity).setAsset(asset).buildCredit();
        creditRepository.save(credit);
        return credit;
    }

    @Override
    protected Balance newBalance(String address, Asset asset, BigInteger quantity) {
        return aBalance().setAddress(address).setAsset(asset).setQuantity(quantity).build();
    }
}
