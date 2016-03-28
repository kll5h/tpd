package com.tilepay.daemon.service;

import static com.tilepay.counterpartyclient.model.CounterpartySendBuilder.aCounterpartySend;
import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.IssuanceBuilder.anIssuance;
import static com.tilepay.domain.entity.TransactionBuilder.aTransaction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.tilepay.counterpartyclient.model.CounterpartySend;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.AssetBuilder;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.BalanceBuilder;
import com.tilepay.domain.entity.Credit;
import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.IssuanceStatus;
import com.tilepay.domain.entity.Transaction;
import com.tilepay.protocol.service.IssuanceService;

public class DaemonTransactionServiceTest extends AbstractPersistenceTest {

    @Inject
    private DaemonTransactionService daemonTransactionService;

    @Inject
    private IssuanceService issuanceService;

    @Before
    public void before() {
        debitRepository.deleteAllInBatch();
        creditRepository.deleteAllInBatch();
        transactionRepository.deleteAllInBatch();
        balanceRepository.deleteAllInBatch();
        messageRepository.deleteAllInBatch();
        assetRepository.deleteAllInBatch();
    }

    @Test
    public void getFirstFreeFeeSend() throws Exception {

        CounterpartySend send = aCounterpartySend().setTx_hash("1").build();
        Transaction transaction = aTransaction().setHash("1").build();

        CounterpartySend firstFreeFeeSend = daemonTransactionService.getFirstFreeFeeSend(Arrays.asList(send), Arrays.asList(transaction));
        assertNull(firstFreeFeeSend);
    }

    @Test
    public void getFirstFreeFeeSend1() throws Exception {

        CounterpartySend send = aCounterpartySend().setTx_hash("1").build();

        CounterpartySend firstFreeFeeSend = daemonTransactionService.getFirstFreeFeeSend(Arrays.asList(send), Arrays.asList());
        assertEquals("1", firstFreeFeeSend.getTx_hash());
    }

    @Test
    public void getFirstFreeFeeSend2() throws Exception {
        CounterpartySend firstFreeFeeSend = daemonTransactionService.getFirstFreeFeeSend(Arrays.asList(), Arrays.asList());
        assertNull(firstFreeFeeSend);
    }

    @Test
    public void getFirstFreeFeeSend3() throws Exception {

        CounterpartySend send = aCounterpartySend().setTx_hash("1").build();
        Transaction transaction = aTransaction().setHash("2").build();

        CounterpartySend firstFreeFeeSend = daemonTransactionService.getFirstFreeFeeSend(Arrays.asList(send), Arrays.asList(transaction));
        assertEquals("1", firstFreeFeeSend.getTx_hash());
    }

    @Test
    public void saveFeeTransaction() {

        Asset asset = saveAsset(anAsset().setName("BBBB").setDivisible(true).setBooked(true).build());
        Issuance message = anIssuance().setAsset(asset).setQuantity(100).setStatus(IssuanceStatus.FEE_IS_NOT_CHARGED).build();
        issuanceService.compose(message);

        Transaction transaction = aTransaction().setHash("1").setSource("source").setData(message.getData()).setDestination("destination").setMessage(message).build();
        transactionRepository.save(transaction);

        CounterpartySend counterpartySend = aCounterpartySend().setTx_hash("2").setSource("source").setDestination("destination").build();
        daemonTransactionService.saveFeeTransaction(counterpartySend, transaction);

        List<Transaction> transactions = transactionRepository.findAll();
        assertEquals(2, transactions.size());

        Transaction assetCreateTransaction = daemonTransactionService.findByHash(transactions, "1");
        assertTransaction(assetCreateTransaction, "1", "source", "destination");
        assertEquals(IssuanceStatus.CREATED, ((Issuance) assetCreateTransaction.getMessage()).getStatus());

        Transaction feeTransaction = daemonTransactionService.findByHash(transactions, "2");
        assertTransaction(feeTransaction, "2", "source", "destination");

        Issuance issuance = (Issuance) feeTransaction.getMessage();
        assertEquals("BBBB", issuance.getAssetName());

        Balance balance = balanceRepository.findByAddressAndAssetName("source", "BBBB");
        assertEquals(new BigInteger("100"), balance.getQuantity());

        List<Credit> credits = creditRepository.findAll();
        assertEquals(1, credits.size());
    }

    @Test
    public void debitFeeTransactionOfExistingEmptyBalance() {

        Asset cntrprtyTilecoinxAsset = AssetBuilder.anAsset().buildCntrprtyTilecoinx();
        assetRepository.save(cntrprtyTilecoinxAsset);

        Balance balance = BalanceBuilder.aBalance().setAsset(cntrprtyTilecoinxAsset).setQuantity(0).setAddress("mk3gU4wM7uxukTbf9DfFXNboQmQiSb9Qu1").build();
        balanceRepository.save(balance);

        Asset asset = saveAsset(anAsset().setName("BBBB").setDivisible(true).setBooked(true).build());
        Issuance message = anIssuance().setAsset(asset).setQuantity(100).setStatus(IssuanceStatus.FEE_IS_NOT_CHARGED).build();
        issuanceService.compose(message);

        Transaction transaction = aTransaction().setHash("1").setSource("mk3gU4wM7uxukTbf9DfFXNboQmQiSb9Qu1").setData(message.getData()).setDestination("destination").setMessage(message).build();
        transactionRepository.save(transaction);

        CounterpartySend counterpartySend = aCounterpartySend().setTx_hash("2").setSource("mk3gU4wM7uxukTbf9DfFXNboQmQiSb9Qu1").setDestination("destination").build();
        daemonTransactionService.saveFeeTransaction(counterpartySend, transaction);

        balance = balanceRepository.findByAddressAndAssetName("mk3gU4wM7uxukTbf9DfFXNboQmQiSb9Qu1", Asset.CNTRPRTY_TILECOINX);
        assertEquals(new BigInteger("50"), balance.getQuantity());

    }

    public void assertTransaction(Transaction transaction, String hash, String source, String destination) {
        assertEquals(hash, transaction.getHash());
        assertEquals(source, transaction.getSource());
        assertEquals(destination, transaction.getDestination());
    }
}