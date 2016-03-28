package com.tilepay.protocol.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import javax.inject.Inject;

import org.junit.Test;

import com.tilepay.domain.entity.Send;
import com.tilepay.domain.entity.SendBuilder;

public class CntrprtySendServiceTest extends AbstractServiceTest {

    @Inject
    private CntrprtySendService cntrprtySendService;

    @Inject
    private ARC4Service arc4Service;

    @Test
    public void compose() throws Exception {

        Send message = SendBuilder.aSend().setAsset(anAsset().setName("DBCA").build()).setQuantity(1000).build();

        cntrprtySendService.compose(message);

        assertTrue(protocolService.isCntrprtyProtocol(message.getData()));

        message = cntrprtySendService.parse(message.getData());
        assertEquals("DBCA", message.getAssetName());
        assertEquals(1000L, message.getQuantity().longValue());
    }

    @Test
    public void parse() {

        byte[] data = new byte[] { -9, -54, -73, -57, 33, -28, 52, 61, 73, -119, -94, -110, -71, 5, 25, 78, -10, -89, 61, -100, -13, -108, 127, -64, 124, 85,
                75, -38, -107, 66, -104, -93, -72, -45, -107, 101, -71, 106, -93, 12, -92, 106, 105, -2, -123, 97, -90, -58, 73, -99, -2, 37, -50, 28, -103,
                15, 49, 82, -66, -57, 77, 124 };

        String key = "b86b3ba98acf5609c3a5679efab3fd6b8f805369c6cee2ebc88549ba091da2d0";

        data = arc4Service.decrypt(key, data);

        Send send = cntrprtySendService.parse(data);

        assertEquals("MASLO", send.getAssetName());
        assertEquals(new BigInteger("200000000"), send.getQuantity());

    }
}