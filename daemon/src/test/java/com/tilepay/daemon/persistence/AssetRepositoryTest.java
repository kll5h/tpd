package com.tilepay.daemon.persistence;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.tilepay.domain.entity.Asset;

public class AssetRepositoryTest extends AbstractRepositoryTest {

    @Before
    public void before() {
        debitRepository.deleteAllInBatch();
        creditRepository.deleteAllInBatch();
        transactionRepository.deleteAllInBatch();
        messageRepository.deleteAllInBatch();
        balanceRepository.deleteAllInBatch();
        assetRepository.deleteAllInBatch();
    }

    @Test
    public void save() {
        Asset asset = anAsset().setName("AAAA").setAssetId("12345").setDivisible(true).setBooked(true).build();
        assetRepository.save(asset);

        asset = assetRepository.findOne(asset.getId());
        assertEquals("AAAA", asset.getName());
        assertEquals("12345", asset.getAssetId());
        assertEquals(true, asset.getDivisible());
        assertEquals(true, asset.getBooked());
    }

    @Test
    public void findByName() {
        Asset asset = anAsset().setName("AAAA").setAssetId("12345").setDivisible(true).setBooked(true).build();
        assetRepository.save(asset);

        asset = assetRepository.findByName(asset.getName());
        assertEquals("AAAA", asset.getName());
        assertEquals("12345", asset.getAssetId());
        assertEquals(true, asset.getBooked());
    }

}