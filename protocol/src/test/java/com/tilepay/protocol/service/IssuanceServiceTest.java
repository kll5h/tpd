package com.tilepay.protocol.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.IssuanceBuilder.anIssuance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;

import com.tilepay.domain.entity.Issuance;

public class IssuanceServiceTest extends AbstractServiceTest {

    @Inject
    private IssuanceService issuanceService;

    @Test
    public void compose() {
        Issuance message = anIssuance().setAsset(anAsset().setName("DBCA").setDivisible(true).build()).setQuantity(1000).build();

        issuanceService.compose(message);

        assertTrue(protocolService.isTilecoinProtocol(message.getData()));

        message = issuanceService.parse(message.getData());

        assertEquals("DBCA", message.getAssetName());
        assertEquals(1000L, message.getQuantity().longValue());
        assertEquals(true, message.getAsset().getDivisible());
    }
}