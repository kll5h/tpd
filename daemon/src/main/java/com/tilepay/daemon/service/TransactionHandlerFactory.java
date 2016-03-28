package com.tilepay.daemon.service;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.tilepay.domain.entity.DeviceRegistration;
import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.Message;
import com.tilepay.domain.entity.Send;

@Component
public class TransactionHandlerFactory {

    @Inject
    private IssuanceHandler issuanceHandler;

    @Inject
    private SendHandler sendHandler;
    
    @Inject
    private DeviceRegistrationHandler deviceRegistrationHandler;

    public TransactionHandler getTransactionHandler(Message message) {
        if (message instanceof Issuance) {
            return issuanceHandler;
        } else if (message instanceof Send) {
            return sendHandler;
        } else if (message instanceof DeviceRegistration) {
            return deviceRegistrationHandler;
        } else {
            throw new IllegalArgumentException("Unknown message type: " + message);
        }
    }

}
