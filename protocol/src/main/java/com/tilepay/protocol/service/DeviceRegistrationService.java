package com.tilepay.protocol.service;

import static com.tilepay.domain.entity.DeviceRegistrationBuilder.aDeviceRegistration;
import static java.util.Arrays.copyOfRange;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.domain.entity.DeviceRegistration;

@Service
public class DeviceRegistrationService extends MessageService<DeviceRegistration> {

    @Inject
    private SHAHashConverter shaHashConverter;

    public void compose(DeviceRegistration deviceRegistration) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(40);
        try {
            byteBuffer.put("IOTC".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        byteBuffer.putInt(DeviceRegistration.messageId);
        byteBuffer.put(shaHashConverter.convertToSHA256Hash(deviceRegistration.getRegistrationToken()));
        deviceRegistration.setData(byteBuffer.array());
    }

    public DeviceRegistration parse(byte[] data) {
        String registrationToken = String.format(Locale.ENGLISH, "%040x", new BigInteger(1, copyOfRange(data, 8, 8 + 32)));
        return aDeviceRegistration().setRegistrationToken(registrationToken).build();
    }
}
