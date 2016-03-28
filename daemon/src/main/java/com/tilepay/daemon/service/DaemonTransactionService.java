package com.tilepay.daemon.service;

import static com.tilepay.domain.entity.TransactionBuilder.aTransaction;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tilepay.counterpartyclient.model.CounterpartySend;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.IssuanceStatus;
import com.tilepay.domain.entity.LedgerEntry;
import com.tilepay.domain.entity.Transaction;
import com.tilepay.protocol.service.IssuanceService;

@Service
public class DaemonTransactionService {

    @Inject
    private DaemonAssetService daemonAssetService;

    @Inject
    private DebitService debitService;

    @Inject
    private CreditService creditService;

    @Inject
    private IssuanceService issuanceService;

    @PersistenceContext
    private EntityManager entityManager;

    public CounterpartySend getFirstFreeFeeSend(List<CounterpartySend> counterpartySends, List<Transaction> transactions) {

        if (transactions.isEmpty() && !counterpartySends.isEmpty()) {
            return counterpartySends.get(0);
        }

        for (CounterpartySend counterpartySend : counterpartySends) {
            if (findByHash(transactions, counterpartySend.getTx_hash()) == null) {
                return counterpartySend;
            }
        }
        return null;
    }

    public Transaction findByHash(List<Transaction> transactions, String hash) {
        for (Transaction transaction : transactions) {
            if (transaction.getHash().equals(hash)) {
                return transaction;
            }
        }
        return null;
    }

    @Transactional
    public void saveFeeTransaction(CounterpartySend counterpartySend, Transaction assetCreateTransaction) {
        Issuance issuance = (Issuance) assetCreateTransaction.getMessage();
        issuance.setStatus(IssuanceStatus.CREATED);

        BigInteger quantity = issuanceService.parse(assetCreateTransaction.getData()).getQuantity();
        LedgerEntry ledgerEntry = creditService.changeBalance(assetCreateTransaction.getSource(), issuance.getAsset(), quantity);
        assetCreateTransaction.addLedgerEntry(ledgerEntry);

        entityManager.merge(assetCreateTransaction);

        Transaction feeTransaction = aTransaction().setHash(counterpartySend.getTx_hash()).setSource(counterpartySend.getSource()).setDestination(counterpartySend.getDestination())
                .setMessage(issuance)
                .build();
        Asset cntrprtyTilecoinxAsset = daemonAssetService.getCntrprtyTilecoinxAsset();
        ledgerEntry = debitService.changeBalance(assetCreateTransaction.getSource(), cntrprtyTilecoinxAsset, new BigInteger("50"));
        feeTransaction.addLedgerEntry(ledgerEntry);
        entityManager.merge(feeTransaction);
    }
}
