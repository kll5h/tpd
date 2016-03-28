package com.tilepay.daemon.persistence;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.IssuanceBuilder.anIssuance;
import static com.tilepay.domain.entity.TransactionBuilder.aTransaction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.IssuanceStatus;
import com.tilepay.domain.entity.Message;
import com.tilepay.domain.entity.Transaction;

public class TransactionRepositoryTest extends AbstractRepositoryTest {

    @Inject
    private TransactionRepository transactionRepository;

    @Before
    public void before() {
        balanceRepository.deleteAllInBatch();
        debitRepository.deleteAllInBatch();
        creditRepository.deleteAllInBatch();
        transactionRepository.deleteAllInBatch();
        messageRepository.deleteAllInBatch();
        assetRepository.deleteAllInBatch();
    }

    @Test
    public void findOne() {
        Asset asset = anAsset().setName("AAAA").setAssetId("12345").setDivisible(true).setBooked(true).build();
        assetRepository.save(asset);

        Message message = anIssuance().setQuantity(1).setAsset(asset).setStatus(IssuanceStatus.FEE_IS_NOT_CHARGED).build();
        Transaction transaction = aTransaction().setSource("source").setMessage(message).build();

        transactionRepository.save(transaction);

        transaction = transactionRepository.findOne(transaction.getId());

        assertEquals("source", transaction.getSource());
        assertTrue(transaction.getMessage() instanceof Issuance);
    }

    //TODO: 21.01.2015 Andrei Sljusar: get issuances?
    @Test
    public void findTransactionsWithoutFeeCharged() {
        Asset asset = anAsset().setName("AAAA").setAssetId("12345").setDivisible(true).setBooked(true).build();
        assetRepository.save(asset);

        Message message = anIssuance().setQuantity(1).setAsset(asset).setStatus(IssuanceStatus.FEE_IS_NOT_CHARGED).build();
        Transaction transaction = aTransaction().setSource("source").setMessage(message).build();
        transactionRepository.save(transaction);

        message = anIssuance().setQuantity(1).setAsset(asset).setStatus(IssuanceStatus.CREATED).build();
        transaction = aTransaction().setSource("source").setMessage(message).build();
        transactionRepository.save(transaction);

        List<Transaction> transactions = transactionRepository.findTransactionsWithoutFeeCharged();
        assertEquals(1, transactions.size());
        assertEquals("source", transactions.get(0).getSource());

        Issuance issuance = (Issuance) transactions.get(0).getMessage();
        assertEquals(IssuanceStatus.FEE_IS_NOT_CHARGED, issuance.getStatus());
    }

    @Test
    public void findIssuanceTransactionsWithoutFeeCharged() {
        List<Transaction> transactions = transactionRepository.findIssuanceTransactionsWithoutFeeCharged("source");
    }

    @Test
    public void findBySourceAndDestination() {
        Asset asset = anAsset().setName("AAAA").setAssetId("12345").setDivisible(true).setBooked(true).build();
        assetRepository.save(asset);

        Message message = anIssuance().setQuantity(1).setAsset(asset).setStatus(IssuanceStatus.FEE_IS_NOT_CHARGED).build();
        Transaction transaction = aTransaction().setSource("source").setDestination("destination").setMessage(message).build();

        transactionRepository.save(transaction);

        List<Transaction> transactions = transactionRepository.findBySourceAndDestination("source", "destination");
        assertEquals(1, transactions.size());
    }

}