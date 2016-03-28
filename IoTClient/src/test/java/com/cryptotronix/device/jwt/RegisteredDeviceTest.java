package com.cryptotronix.device.jwt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nimbusds.jwt.SignedJWT;

public class RegisteredDeviceTest {

    public RegisteredDevice d;

    @Before
    public void setUp() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        this.d = new DeviceSoft();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAPI() throws Exception {
        System.out.println(d.getCryptoSerial());
        System.out.println(d.getSystemSerial());

        assertEquals(d.getRegistrationToken(), SignedJWT.parse(d.getRegistrationToken()).serialize());

        assertNotNull(d.getPublicKey());

        String signed = d.sign("test");

        assertTrue(d.verify(signed));

    }

}
