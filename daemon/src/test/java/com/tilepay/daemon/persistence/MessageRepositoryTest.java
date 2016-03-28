package com.tilepay.daemon.persistence;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.IssuanceBuilder.anIssuance;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.IssuanceStatus;

public class MessageRepositoryTest extends AbstractRepositoryTest {

    @Before
    public void before() {
        debitRepository.deleteAllInBatch();
        creditRepository.deleteAllInBatch();
        balanceRepository.deleteAllInBatch();
        messageRepository.deleteAllInBatch();
        assetRepository.deleteAllInBatch();
    }

    @Test
    public void findIssuanceByAssetName() {
        Asset asset = anAsset().setName("AAAA").setDivisible(true).setBooked(true).setAssetId("12345").build();
        assetRepository.save(asset);

        Issuance issuance = anIssuance().setAsset(asset).setStatus(IssuanceStatus.FEE_IS_NOT_CHARGED).setQuantity(1000).build();
        messageRepository.save(issuance);

        issuance = messageRepository.findIssuanceByAssetName("AAAA");
        assertEquals("AAAA", issuance.getAssetName());
    }

}