package com.tilepay.protocol.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.bitcoinj.core.Utils;
import org.springframework.stereotype.Service;

@Service
public class ByteService {

    public byte[] createChunkKey(byte[] chunk) {
        byte[] dest = new byte[33];
        System.arraycopy(chunk, 0, dest, 1, chunk.length);
        return dest;
    }

    public byte[] getBytesWithoutFirstLast(byte[] data) {
        return Arrays.copyOfRange(data, 1, data.length - 1);
    }

    public byte[] toByteArray(BigInteger bigInteger) {
        byte[] destination = new byte[8];
        byte[] source = bigInteger.toByteArray();

        if (source.length == 9) {
            source = Arrays.copyOfRange(source, 1, source.length);
        }

        System.arraycopy(source, 0, destination, destination.length - source.length, source.length);
        return destination;
    }

    public void putBytes(ByteBuffer byteBuffer, int index, byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            byteBuffer.put(i + index, bytes[i]);
        }
    }

    public BigInteger getBigInteger(String hex) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(Utils.parseAsHexOrBase58(hex));
        return new BigInteger(1, byteBuffer.array());
    }

    public byte[] toByteArray(byte[]... byteArrays) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (byte[] byteArray : byteArrays) {
            try {
                outputStream.write(getBytesWithoutFirstLast(byteArray));
            } catch (IOException e) {
                throw new RuntimeException("TODO: ", e);
            }
        }
        return outputStream.toByteArray();
    }
}
