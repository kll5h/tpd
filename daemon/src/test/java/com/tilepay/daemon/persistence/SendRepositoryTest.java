package com.tilepay.daemon.persistence;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Message;
import com.tilepay.domain.entity.SendBuilder;

public class SendRepositoryTest extends AbstractRepositoryTest {

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

        Message send = SendBuilder.aSend().setAsset(asset).build();

        messageRepository.save(send);

        send = messageRepository.findOne(send.getId());
        assertEquals("ABCD", send.getAssetName());
    }

}