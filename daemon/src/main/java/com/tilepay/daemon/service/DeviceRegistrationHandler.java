package com.tilepay.daemon.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import com.tilepay.domain.entity.Device;
import com.tilepay.domain.entity.DeviceRegistration;
import com.tilepay.domain.entity.Transaction;

@Service
public class DeviceRegistrationHandler extends TransactionHandler<DeviceRegistration> {

    public void handle(Transaction tx, DeviceRegistration deviceRegistration) {
        String registrationToken =  deviceRegistration.getRegistrationToken();
        
        Device device = new Device();
        device.setJwt(String.format("%040x", new BigInteger(1, registrationToken.getBytes())));
        
        System.out.println("saving device " + device.getJwt());
        
        deviceService.save(device);
    }

    @Override
    public void handle(Transaction tx) {
        handle(tx, (DeviceRegistration) tx.getMessage());
    }
}
