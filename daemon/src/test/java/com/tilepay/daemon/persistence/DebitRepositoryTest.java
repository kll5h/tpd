package com.tilepay.daemon.persistence;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.LedgerEntryBuilder.aLedgerEntry;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Debit;

public class DebitRepositoryTest extends AbstractRepositoryTest {

    @Inject
    private BalanceRepository balanceRepository;

    @Inject
    private AssetRepository assetRepository;

    @Inject
    private DebitRepository debitRepository;

    @Before
    public void before() {
        messageRepository.deleteAllInBatch();
        balanceRepository.deleteAllInBatch();
        debitRepository.deleteAllInBatch();
        assetRepository.deleteAllInBatch();
    }

    @Test
    public void save() {
        Asset asset = anAsset().setAssetId("12345").setBooked(true).setName("ABCDE").setDivisible(true).build();
        assetRepository.save(asset);

        Debit debit = aLedgerEntry().setAddress("address").setQuantity(10000).setAsset(asset).buildDebit();

        debitRepository.save(debit);

        debit = debitRepository.findOne(debit.getId());
        assertEquals("ABCDE", debit.getAsset().getName());
        assertEquals("address", debit.getAddress());
        assertEquals(BigInteger.valueOf(10000), debit.getQuantity());
    }

}