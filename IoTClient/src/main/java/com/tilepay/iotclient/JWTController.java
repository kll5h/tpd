package com.tilepay.iotclient;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptotronix.device.jwt.DeviceSoft;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

@RestController
public class JWTController {

    private DeviceSoft ds;

    @RequestMapping("/data")
    public String index() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, JOSEException {
        Security.addProvider(new BouncyCastleProvider());
        this.ds = new DeviceSoft();

        Runtime runtime = Runtime.getRuntime();

        int mb = 1024 * 1024;

        String data = String.valueOf((runtime.totalMemory() - runtime.freeMemory()) / mb);

        System.out.println(data);

        SignedJWT jwt = ds.toSignedJWT(data);

        Boolean verify = ds.verify(jwt);

        return jwt.serialize();
    }

    @RequestMapping("/register")
    public String register() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, JOSEException {
        Security.addProvider(new BouncyCastleProvider());
        this.ds = new DeviceSoft();

        SignedJWT jwt = ds.toSignedJWT();

        Boolean verify = ds.verify(jwt);

        return jwt.serialize();
    }

}