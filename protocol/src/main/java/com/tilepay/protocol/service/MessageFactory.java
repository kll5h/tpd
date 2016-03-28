package com.tilepay.protocol.service;

import java.nio.ByteBuffer;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.tilepay.domain.entity.DeviceRegistration;
import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.Message;
import com.tilepay.domain.entity.Send;

@Component
public class MessageFactory {

    @Inject
    private IssuanceService issuanceService;

    @Inject
    private SendService sendService;
    
    @Inject
    private DeviceRegistrationService deviceRegistrationService;

    public Message get(byte[] data) {
        Integer messageId = getMessageId(data, 8);

        if (messageId.equals(Issuance.messageId)) {
            return issuanceService.parse(data);
        } else if (messageId.equals(Send.messageId)) {
            return sendService.parse(data);
        } else if (getMessageId(data,4).equals(DeviceRegistration.messageId)) {
            return deviceRegistrationService.parse(data);
        }

        throw new IllegalArgumentException("TODO: unknown message id: " + messageId);
    }

    public Integer getMessageId(byte[] data, int index) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        return byteBuffer.getInt(index);
    }
    
}
