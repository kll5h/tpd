package com.tilepay.core.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.io.BaseEncoding;
import com.tilepay.core.config.CoreAppConfig;
import com.tilepay.protocol.service.ARC4;
import com.tilepay.protocol.service.AssetService;
import com.tilepay.protocol.service.ByteService;

//        String binary = new BigInteger(hex, 16).toString(2);
//        long i = Long.parseLong(binary, 2);
@ContextConfiguration(classes = CoreAppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unittest")
public class BytesTest {

    @Inject
    private AssetService assetService;

    @Inject
    private ByteService byteService;

    BaseEncoding HEX = BaseEncoding.base16().lowerCase();

    @Test
    public void parseHex() {
        extractData("1c434e5452505254590000000000000000060a27bf0000000ba43b740000000000000000000000000000000000000000000000000000000000000000000000000000");
    }

    @Test
    public void decrypt() {
        byte[] cipherText = publicKeyTo("bf14ef94690b1e177813a3f68a358b7c48ea09a89adb9a0153b1bd69795251", "569e7a0973c01e20c813dea8a40d8210ca78f15df3a046862e9ff15c6538d6");
        ARC4 key = new ARC4(HEX.decode("3e92f442feef486659b0b9d2aefd9e3698ffac3d9bb74706dad60fc3ef3e5a89"));
        byte[] plainText = new byte[cipherText.length];
        key.crypt(cipherText, plainText);
        String data = HEX.encode(plainText);
        extractData(data);
    }

    private byte[] publicKeyTo(String... hashes) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (String hash : hashes) {
            try {
                outputStream.write(HEX.decode(hash));
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return outputStream.toByteArray();
    }

    private void extractData(String hex) {
        try {
            System.out.println("Prefix: " + new String(Hex.decodeHex(hex.substring(2, 18).toCharArray()), "UTF-8"));
        } catch (DecoderException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("********Asset name: " + assetService.getAssetName(byteService.getBigInteger(hex.substring(26, 42))));
        System.out.println("********Amount: " + byteService.getBigInteger(hex.substring(42, 42 + 16)));
    }

}
