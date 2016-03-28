package com.tilepay.daemon.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.IssuanceBuilder.anIssuance;
import static com.tilepay.domain.entity.TransactionBuilder.aTransaction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.IssuanceStatus;
import com.tilepay.domain.entity.Transaction;

public class IssuanceHandlerTest extends AbstractPersistenceTest {

    @Inject
    private IssuanceHandler issuanceHandler;

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
    public void newIssuanceWithoutFee() {

        Asset asset = anAsset().setName("AAAA").setDivisible(true).setAssetId("12345").build();
        Issuance issuance = anIssuance().setAsset(asset).setQuantity(1000).build();
        Transaction tx = aTransaction().setMessage(issuance).setSource("source").build();

        issuanceHandler.handle(tx);

        asset = assetRepository.findByName("AAAA");
        assertEquals("AAAA", asset.getName());
        assertEquals(true, asset.getBooked());

        issuance = (Issuance) messageRepository.findOne(issuance.getId());
        IssuanceAsserter.assertIssuance(issuance, IssuanceStatus.FEE_IS_NOT_CHARGED);
        assertEquals("AAAA", issuance.getAssetName());
    }

    @Test
    public void issueNewAsset() {
        Asset asset = anAsset().setName("AAAA").setDivisible(true).setAssetId("12345").build();
        Issuance issuance = anIssuance().setAsset(asset).setQuantity(1000).build();
        Transaction tx = aTransaction().setMessage(issuance).setSource("source").build();

        issuanceHandler.handle(tx);

        asset = assetRepository.findByName("AAAA");
        assertEquals("AAAA", asset.getName());
        assertEquals(true, asset.getBooked());

        issuance = (Issuance) messageRepository.findOne(issuance.getId());
        assertEquals("AAAA", issuance.getAssetName());

        Balance balance = balanceRepository.findByAddressAndAssetName("source", "AAAA");
        assertNull(balance);
    }

    @Test
    public void issueBookedAsset() {
        Asset asset = anAsset().setName("AAAA").setDivisible(true).setBooked(true).build();
        assetRepository.save(asset);

        asset = anAsset().setName("AAAA").setDivisible(true).build();
        Issuance issuance = anIssuance().setAsset(asset).setQuantity(1000).build();
        Transaction tx = aTransaction().setMessage(issuance).setSource("source").build();

        issuanceHandler.handle(tx);

        issuance = (Issuance) messageRepository.findOne(issuance.getId());
        IssuanceAsserter.assertIssuance(issuance, IssuanceStatus.FEE_IS_NOT_CHARGED);
        assertEquals("AAAA", issuance.getAssetName());
    }

    @Test
    public void ignoreIssuingCreatedAsset() {
        Asset asset = anAsset().setName("AAAA").setDivisible(true).setBooked(true).build();
        assetRepository.save(asset);
        Issuance issuance = anIssuance().setAsset(asset).setStatus(IssuanceStatus.CREATED).setQuantity(1000).build();
        messageRepository.save(issuance);

        issuance = anIssuance().setAsset(anAsset().setName("AAAA").setDivisible(true).build()).setQuantity(1000).build();
        Transaction tx = aTransaction().setMessage(issuance).setSource("source").build();

        try {
            issuanceHandler.handle(tx);
            fail("Should throw " + IssuedByAnotherAddressException.class);
        } catch (IssuedByAnotherAddressException e) {
            assertEquals("Asset: AAAA issued by another address: ", e.getMessage());
        }

        asset = assetRepository.findByName("AAAA");
        assertEquals("AAAA", asset.getName());
        assertEquals(true, asset.getBooked());
    }

}