package com.tilepay.protocol.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static java.util.Arrays.copyOfRange;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import org.springframework.stereotype.Service;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Send;
import com.tilepay.domain.entity.SendBuilder;

//PREFIX + messageId + assetId + quantity
@Service
public class SendService extends MessageService<Send> {

    //TODO: 22.12.2014 Andrei Sljusar: data_chunk = bytes([len(data_chunk)]) + data_chunk + (pad_length * b'\x00')
    public void compose(Send message) {
        //TODO: 10.12.2014 Andrei Sljusar: length of message (message type). 33 is length of public key?
        //TODO: 18.12.2014 Andrei Sljusar: ByteBuffer byteBuffer = ByteBuffer.allocate(length + 4);
        ByteBuffer byteBuffer = ByteBuffer.allocate(33);

        BigInteger assetId = assetService.getAssetId(message.getAssetName());
        byteBuffer.putInt(0, Send.messageId);
        byteService.putBytes(byteBuffer, 4, byteService.toByteArray(assetId));
        byteService.putBytes(byteBuffer, 13, byteService.toByteArray(message.getQuantity()));
        byte[] data = setPrefix(byteBuffer);
        message.setData(data);
    }

    public Send parse(byte[] data) {
        BigInteger assetId = new BigInteger(1, copyOfRange(data, 12, 12 + 8));
        Asset asset = anAsset().setName(assetService.getAssetName(assetId)).build();
        BigInteger quantity = new BigInteger(1, copyOfRange(data, 21, 21 + 8));
        return SendBuilder.aSend().setAsset(asset).setQuantity(quantity).build();
    }

}
