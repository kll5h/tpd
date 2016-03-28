package com.tilepay.daemon.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.BalanceBuilder.aBalance;
import static com.tilepay.domain.entity.SendBuilder.aSend;
import static com.tilepay.domain.entity.TransactionBuilder.aTransaction;

import java.math.BigInteger;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Send;
import com.tilepay.domain.entity.Transaction;

public class SendHandlerTest extends AbstractPersistenceTest {

    @Inject
    private SendHandler sendHandler;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before() {
        transactionRepository.deleteAllInBatch();
        creditRepository.deleteAllInBatch();
        debitRepository.deleteAllInBatch();
        debitRepository.deleteAllInBatch();
        balanceRepository.deleteAllInBatch();
        messageRepository.deleteAllInBatch();
        assetRepository.deleteAllInBatch();
    }

    @Test
    public void send() {
        Asset asset = assetRepository.save(anAsset().setDivisible(true).setBooked(true).setName("AAAA").build());
        balanceRepository.save(aBalance().setAsset(asset).setAddress("source").setQuantity(1000).build());

        Send send = aSend().setAsset(asset).setQuantity(1).build();
        Transaction tx = aTransaction().setMessage(send).setSource("source").setDestination("destination").build();

        sendHandler.handle(tx);

        assertBalance(balanceRepository.findByAddressAndAssetName("source", "AAAA"), "source", new BigInteger("999"));
        assertBalance(balanceRepository.findByAddressAndAssetName("destination", "AAAA"), "destination", new BigInteger("1"));
    }

    @Test
    public void assetDoesNotExistException() {

        Send send = aSend().setAsset(anAsset().setName("AAAA").setDivisible(true).setBooked(true).build()).build();
        Transaction tx = aTransaction().setMessage(send).build();

        thrown.expect(AssetDoesNotExistException.class);
        thrown.expectMessage("AAAA does not exist");
        sendHandler.handle(tx);
    }

}