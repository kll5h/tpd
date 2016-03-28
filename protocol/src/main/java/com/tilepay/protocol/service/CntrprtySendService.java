package com.tilepay.protocol.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static java.util.Arrays.copyOfRange;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Protocol;
import com.tilepay.domain.entity.Send;
import com.tilepay.domain.entity.SendBuilder;

//length + PREFIX + messageId + assetId + quantity
@Service
public class CntrprtySendService extends MessageService<Send> {

    public void compose(Send message) {
        //TODO: 04.01.2015 Andrei Sljusar: buffer size
        ByteBuffer byteBuffer = ByteBuffer.allocate(62);
        //TODO: 29.12.2014 Andrei Sljusar: length
        //pad_length = (33 * 2) - 1 - 2 - 2 - len(data_chunk)
        byteBuffer.put(0, (byte) 28);
        BigInteger assetId = assetService.getAssetId(message.getAssetName());
        byteService.putBytes(byteBuffer, 1, Protocol.CNTRPRTY.getBytes());
        byteService.putBytes(byteBuffer, 13, byteService.toByteArray(assetId));
        //TODO: 04.01.2015 Andrei Sljusar: Long?
        byteBuffer.putLong(21, message.getQuantity().longValue());
        message.setData(byteBuffer.array());
    }

    public Send parse(byte[] data) {
        BigInteger assetId = new BigInteger(1, copyOfRange(data, 13, 13 + 8));
        Asset asset = anAsset().setName(assetService.getAssetName(assetId)).build();
        BigInteger quantity = new BigInteger(1, Arrays.copyOfRange(data, 21, 21 + 8));
        return SendBuilder.aSend().setAsset(asset).setQuantity(quantity).build();
    }

}
