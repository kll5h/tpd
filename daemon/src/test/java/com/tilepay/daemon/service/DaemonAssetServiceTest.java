package com.tilepay.daemon.service;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.AssetBuilder;

public class DaemonAssetServiceTest extends AbstractPersistenceTest {

    @Inject
    private DaemonAssetService daemonAssetService;

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
    public void saveAsset() {
        Asset asset = AssetBuilder.anAsset().setName("AAAA").setDivisible(true).setBooked(true).build();
        daemonAssetService.saveAsset(asset);
    }

}