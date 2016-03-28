package com.tilepay.protocol.service;

import static java.util.Arrays.copyOfRange;

import java.nio.ByteBuffer;

import javax.inject.Inject;

import com.tilepay.domain.entity.Message;
import com.tilepay.domain.entity.Protocol;

public abstract class MessageService<T extends Message> {

    @Inject
    protected AssetService assetService;

    @Inject
    protected ByteService byteService;

    public abstract void compose(T message);

    public abstract T parse(byte[] data);

    public byte[] setPrefix(ByteBuffer byteBuffer) {
        byte[] source = byteBuffer.array();
        byte[] destination = copyOfRange(Protocol.TILECOIN.getBytes(), 0, 33);
        System.arraycopy(source, 0, destination, 8, source.length - 8);
        return destination;
    }

}
