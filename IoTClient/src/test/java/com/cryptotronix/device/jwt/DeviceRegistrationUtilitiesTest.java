package com.cryptotronix.device.jwt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.KeyUse;

public class DeviceRegistrationUtilitiesTest {

    @Before
    public void setUp() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void genereateKeyPairTest() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {

        assertNotNull(DeviceRegistrationUtilities.generateKeyPair());

    }

    @Test
    public void generateNewJWKTest() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        ECKey keyPair = DeviceRegistrationUtilities.generateNewJWK();

        assertNotNull(keyPair);

        System.out.println(keyPair.toJSONString());

        assertEquals(JWSAlgorithm.ES256, keyPair.getAlgorithm());
        assertEquals(KeyUse.SIGNATURE, keyPair.getKeyUse());
        assertEquals(ECKey.Curve.P_256, keyPair.getCurve());
        assertNotNull(keyPair.toECPrivateKey());
        assertNotNull(keyPair.toECPublicKey());

    }

}
