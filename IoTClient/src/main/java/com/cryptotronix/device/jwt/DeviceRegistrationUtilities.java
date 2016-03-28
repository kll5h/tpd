package com.cryptotronix.device.jwt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import org.bouncycastle.jce.ECNamedCurveTable;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class DeviceRegistrationUtilities {

    // public DeviceRegistrationUtilities(String encodedSignedJWT) throws
    // ParseException,
    // IOException, NoSuchAlgorithmException, InvalidKeySpecException,
    // SignatureException {
    //
    // this.jwt = (SignedJWT) JWTParser.parse(encodedSignedJWT);
    //
    // this.pub = (ECPublicKey) this.verify(this.jwt);
    //
    // }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        // Create the public and private EC keys
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
        keyGenerator.initialize(ECNamedCurveTable.getParameterSpec("P-256"));
        return keyGenerator.generateKeyPair();

    }

    public static ECKey generateNewJWK() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        KeyPair pair = generateKeyPair();

        return toPrivateJWK((ECPublicKey) pair.getPublic(), (ECPrivateKey) pair.getPrivate());

    }

    public static ECKey toPublicJWK(ECPublicKey key) {
        return new ECKey(ECKey.Curve.P_256, key, KeyUse.SIGNATURE, null, JWSAlgorithm.ES256, null, null, null, null);
    }

    public static ECKey toPrivateJWK(ECPublicKey pubKey, ECPrivateKey privKey) {
        return new ECKey(ECKey.Curve.P_256, pubKey, privKey, KeyUse.SIGNATURE, null, JWSAlgorithm.ES256, String.valueOf(System.currentTimeMillis()), null,
                null, null);

    }

    public static JWTClaimsSet buildRegClaims(RegisteredDevice info, ECKey pubKey) throws IOException {
        JWTClaimsSet claimsSet = new JWTClaimsSet();

        claimsSet.setSubject(info.getSystemSerial());
//        claimsSet.setIssueTime(new Date());
        claimsSet.setIssuer(info.getCryptoSerial());
        claimsSet.setCustomClaim("pubKey", pubKey.toJSONString());
        claimsSet.setCustomClaim("Version", 1);

        return claimsSet;
    }

    public static JWTClaimsSet buildDataClaims(RegisteredDevice info, ECKey pubKey, String data) throws IOException {
        JWTClaimsSet claimsSet = buildRegClaims(info, pubKey);

//        claimsSet.setCustomClaim("data", data);
        claimsSet.setCustomClaim("description", "A weather station in Reykjavik");
        claimsSet.setCustomClaim("name", "reykjavik station 1");
        
        return claimsSet;
    }

    public static SignedJWT createRegistrationData(RegisteredDevice info, ECKey jwk) throws JOSEException, IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {

        // Get the keys
        ECPrivateKey priv = jwk.toECPrivateKey();

        // Create the EC signer
        JWSSigner signer = new ECDSASigner(priv.getS());

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = buildRegClaims(info, jwk.toPublicJWK());

        SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.ES256), claimsSet);

        // Compute the EC signature
        jwt.sign(signer);

        return jwt;

    }

    public static SignedJWT createSignedData(RegisteredDevice info, ECKey jwk, String data) throws JOSEException, IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {

        // Get the keys
        ECPrivateKey priv = jwk.toECPrivateKey();

        // Create the EC signer
        JWSSigner signer = new ECDSASigner(priv.getS());

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = buildDataClaims(info, jwk.toPublicJWK(), data);

        SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.ES256), claimsSet);

        // Compute the EC signature
        jwt.sign(signer);

        return jwt;

    }

    public static String loadFileAsString(File f) throws IOException {
        return new String(Files.readAllBytes(f.toPath()));
    }

}
