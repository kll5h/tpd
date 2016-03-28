package com.tilepay.daemon.persistence;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.IssuanceBuilder.anIssuance;
import static com.tilepay.domain.entity.IssuanceStatus.FEE_IS_NOT_CHARGED;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Issuance;

public class IssuanceRepositoryTest extends AbstractRepositoryTest {

    @Before
    public void before() {
        debitRepository.deleteAllInBatch();
        balanceRepository.deleteAllInBatch();
        messageRepository.deleteAllInBatch();
        assetRepository.deleteAllInBatch();
    }

    @Test
    public void findOne() {
        Asset asset = anAsset().setName("ABCD").setDivisible(true).setBooked(true).build();
        assetRepository.save(asset);

        Issuance issuance = anIssuance().setAsset(asset).setStatus(FEE_IS_NOT_CHARGED).build();

        messageRepository.save(issuance);

        issuance = (Issuance) messageRepository.findOne(issuance.getId());
        assertEquals("ABCD", issuance.getAssetName());
        assertEquals(FEE_IS_NOT_CHARGED, issuance.getStatus());
    }

}