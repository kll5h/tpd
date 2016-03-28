package com.tilepay.protocol.service;

import org.springframework.stereotype.Service;

import com.google.common.io.BaseEncoding;

@Service
public class ARC4Service {

    private BaseEncoding HEX = BaseEncoding.base16().lowerCase();

    public byte[] decrypt(String keyCode, byte[] hashes) {
        ARC4 key = new ARC4(HEX.decode(keyCode));
        byte[] plainText = new byte[hashes.length];
        key.crypt(hashes, plainText);
        return plainText;
    }

    public void crypt(byte[] dataChunk, String inputHash) {
        ARC4 ARC4crypter = new ARC4(HEX.decode(inputHash));
        ARC4crypter.crypt(dataChunk);
    }
}
