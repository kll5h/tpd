package com.tilepay.protocol.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;

import com.tilepay.domain.entity.Send;
import com.tilepay.domain.entity.SendBuilder;

public class SendServiceTest extends AbstractServiceTest {

    @Inject
    private SendService sendService;

    @Test
    public void compose() {
        Send message = SendBuilder.aSend().setAsset(anAsset().setName("DBCA").build()).setQuantity(1000).build();

        sendService.compose(message);

        assertTrue(protocolService.isTilecoinProtocol(message.getData()));

        message = sendService.parse(message.getData());

        assertEquals("DBCA", message.getAssetName());
        assertEquals(1000L, message.getQuantity().longValue());

    }
    
    @Test
    public void parse() {
        Send message = SendBuilder.aSend().setAsset(anAsset().setName("DBCA").build()).setQuantity(1000).build();

        sendService.compose(message);

        assertTrue(protocolService.isTilecoinProtocol(message.getData()));

        message = sendService.parse(message.getData());

        assertEquals("DBCA", message.getAssetName());
        assertEquals(1000L, message.getQuantity().longValue());

    }

}