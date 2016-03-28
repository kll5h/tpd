package com.cryptotronix.device.jwt;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jwt.SignedJWT;

/**
 * Abstract class that represents a device. A device contains at minimum, it's
 * SignedJWT registration data and a public key.
 * 
 * @author Josh Datko <jbd@cryptotronix.com>
 *
 */
public abstract class Device implements RegisteredDevice {

    /**
     * The Signed JWT registration data.
     */
    protected SignedJWT jwt;

    /**
     * The Devic'es public Key
     */
    protected ECPublicKey pub;

    public String toString() {
        return this.jwt.serialize();
    }

    public ECPublicKey getPublicKey() {
        return this.pub;
    }

    /**
     * @return The Device's signed Registraiton JWT
     */
    public SignedJWT getJWT() {
        return this.jwt;
    }

    /**
     * Verifies a signed
     * 
     * @param jws
     * @return
     */
    public boolean verify(SignedJWT jws) throws ParseException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException,
            JOSEException {

        ECKey pubKey = ECKey.parse(jws.getJWTClaimsSet().getAllClaims().get("pubKey").toString());
        ECPublicKey pub = pubKey.toECPublicKey();

        BigInteger x = pub.getW().getAffineX();
        BigInteger y = pub.getW().getAffineY();
        JWSVerifier verifier = new ECDSAVerifier(x, y);

        return jwt.verify(verifier);

    }

    public boolean verify(String token) throws IOException {
        SignedJWT jws;
        try {
            jws = SignedJWT.parse(token);
            return this.verify(jws);
        } catch (ParseException | NoSuchAlgorithmException | InvalidKeySpecException | SignatureException | IOException | JOSEException e) {
            throw new IOException(e);
        }

    }

    public String getRegistrationToken() {
        return this.jwt.serialize();
    }

    public abstract SignedJWT toSignedJWT(String data) throws IOException;

    public String sign(String data) throws IOException {
        return this.toSignedJWT(data).serialize();
    }

}
