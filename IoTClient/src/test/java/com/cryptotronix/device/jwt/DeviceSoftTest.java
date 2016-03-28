/**
 * 
 */
package com.cryptotronix.device.jwt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nimbusds.jwt.SignedJWT;

/**
 * @author Josh Datko <jbd@cryptotronix.com>
 *
 */
public class DeviceSoftTest {

    public DeviceSoft ds;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        this.ds = new DeviceSoft();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConstructor() {
        assertNotNull(ds.getPublicKey());
        assertNotNull(ds.getJWT());

    }

    @Test
    public void testGetCryptoSerial() {
        assertEquals("000000000000000000", ds.getCryptoSerial());
    }

    @Test
    public void testGetSystemSerial() throws IOException {
        assertNotNull(ds.getSystemSerial());

        System.out.println(ds.getSystemSerial());
    }

    @Test
    public void testToSignedJWT() throws Exception {
        Runtime runtime = Runtime.getRuntime();

        int mb = 1024 * 1024;

        String data = String.valueOf((runtime.totalMemory() - runtime.freeMemory()) / mb);

        System.out.println(data);

        SignedJWT jwt = ds.toSignedJWT(data);

        assertNotNull(jwt);

        assertTrue(ds.verify(jwt));

        System.out.println(jwt.serialize());
        System.out.println(jwt.toString());

        //TODO: 13.02.2015 Andrei Sljusar: java.lang.NullPointerException
        assertEquals(data, jwt.getJWTClaimsSet().getAllClaims().get("data").toString());

    }

}
