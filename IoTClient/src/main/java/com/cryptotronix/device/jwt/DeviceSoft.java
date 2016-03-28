package com.cryptotronix.device.jwt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Properties;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jwt.SignedJWT;

public class DeviceSoft extends Device {

    protected ECKey privateKey;

    public DeviceSoft() throws IOException {
        this.loadInfo();
    }

    @Override
    public String getSystemSerial() throws IOException {

        // taken from http://www.mkyong.com/java/how-to-get-mac-address-in-java/
        InetAddress ip = InetAddress.getLocalHost();
        //System.out.println("Current IP address : " + ip.getHostAddress());
        
        final StringBuilder sb = new StringBuilder();

        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        
        if(network == null){
            //System.out.println("---------------------------------------lo");
            network = NetworkInterface.getByName("lo");
        }
        
        if(network == null){
            //System.out.println("---------------------------------------eth0");
            network = NetworkInterface.getByName("eth0");
        }
        
        if(network == null){
            //System.out.println("---------------------------------------wlan0");
            network = NetworkInterface.getByName("wlan0");
        }
        
        byte[] mac = network.getHardwareAddress();
        if (mac == null) {
            mac = new String("00-00-00-00-00-00").getBytes();
        }
        
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], ""));
        }

        return sb.toString();
    }

    @Override
    public String getCryptoSerial() {

        // Software Serial's are all zeros
        return new String("000000000000000000");
    }

    protected void writeRegistrationData(File f_pub, File f_pri, File f_jwt) throws IOException {
        try {
            this.privateKey = DeviceRegistrationUtilities.generateNewJWK();

            this.jwt = DeviceRegistrationUtilities.createRegistrationData(this, this.privateKey);

            BufferedWriter w_pub = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f_pub)));
            BufferedWriter w_pri = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f_pri)));
            BufferedWriter w_jwt = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f_jwt)));

            w_jwt.write(this.jwt.serialize());
            w_pub.write(this.privateKey.toPublicJWK().toJSONString());
            w_pri.write(this.privateKey.toJSONString());

            w_jwt.close();
            w_pub.close();
            w_pri.close();

            this.pub = this.privateKey.toECPublicKey();

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | InvalidKeySpecException | JOSEException e) {
            throw new IOException(e);
        }
    }

    protected void loadInfo() throws IOException {
        Properties prop = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("device.properties");

        prop.load(in);
        in.close();
        File home = new File(System.getProperty("user.home"));

        File f_pub = new File(home, prop.getProperty("reg_pub.jwk"));
        File f_pri = new File(home, prop.getProperty("reg_pri.jwk"));
        File f_jwt = new File(home, prop.getProperty("reg.jwt"));

        f_pub.getParentFile().mkdirs();

        if (f_pub.createNewFile() || f_pri.createNewFile() || f_jwt.createNewFile()) {
            this.writeRegistrationData(f_pub, f_pri, f_jwt);

        } else {

            try {
                this.privateKey = ECKey.parse(DeviceRegistrationUtilities.loadFileAsString(f_pri));
                this.jwt = SignedJWT.parse(DeviceRegistrationUtilities.loadFileAsString(f_jwt));
                this.pub = this.privateKey.toECPublicKey();

            } catch (ParseException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new IOException(e);
            }
        }

    }

    public SignedJWT toSignedJWT(String data) throws IOException {
        try {
            return DeviceRegistrationUtilities.createSignedData(this, this.privateKey, data);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | JOSEException | IOException e) {
            throw new IOException(e);
        }
    }

    public SignedJWT toSignedJWT() throws IOException {
        try {
            return DeviceRegistrationUtilities.createRegistrationData(this, this.privateKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | JOSEException | IOException e) {
            throw new IOException(e);
        }
    }

}
