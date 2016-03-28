package com.tilepay.protocol.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.IssuanceBuilder.anIssuance;
import static java.util.Arrays.copyOfRange;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import org.springframework.stereotype.Service;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Issuance;


//TODO: 19.01.2015 Andrei Sljusar: = fee tx hash?
@Service
public class IssuanceService extends MessageService<Issuance> {

    //TODO: 22.12.2014 Andrei Sljusar: data_chunk = bytes([len(data_chunk)]) + data_chunk + (pad_length * b'\x00')
    public void compose(Issuance issuance) {
        BigInteger assetId = assetService.getAssetId(issuance.getAssetName());
        //TODO: 10.12.2014 Andrei Sljusar: length of message (message type). 33 is length of public key?
        //TODO: 18.12.2014 Andrei Sljusar: ByteBuffer byteBuffer = ByteBuffer.allocate(length + 4);
        ByteBuffer byteBuffer = ByteBuffer.allocate(33);

        //TODO: 28.12.2014 Andrei Sljusar: defferent message id
        //byteBuffer.putInt(0, issuance.getMessageId());
        byteBuffer.putInt(0, Issuance.messageId);

        byteService.putBytes(byteBuffer, 4, byteService.toByteArray(assetId));
        //TODO: 13.01.2015 Andrei Sljusar: divisible/indivisible
        byteService.putBytes(byteBuffer, 13, byteService.toByteArray(issuance.getQuantity()));
        byteBuffer.put(22, (byte) (issuance.getAsset().getDivisible() ? 1 : 0));
        byte[] destination = setPrefix(byteBuffer);
        issuance.setData(destination);
    }

    //TODO: 23.12.2014 Andrei Sljusar: Please note that a 0.5 XCP fee will be deducted from your balance upon successful creation.
    public Issuance parse(byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        BigInteger assetId = new BigInteger(1, copyOfRange(data, 12, 12 + 8));
        Boolean divisible = (byteBuffer.get(30) == 1);
        Asset asset = anAsset().setName(assetService.getAssetName(assetId)).setDivisible(divisible).build();
        BigInteger quantity = new BigInteger(1, copyOfRange(data, 21, 21 + 8));
        return anIssuance().setAsset(asset).setQuantity(quantity).build();
    }
}
