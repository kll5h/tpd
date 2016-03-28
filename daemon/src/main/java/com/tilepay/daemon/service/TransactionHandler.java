package com.tilepay.daemon.service;

import javax.inject.Inject;

import com.tilepay.daemon.persistence.AssetRepository;
import com.tilepay.daemon.persistence.MessageRepository;
import com.tilepay.domain.entity.Message;
import com.tilepay.domain.entity.Transaction;

public abstract class TransactionHandler<T extends Message> {

    @Inject
    protected DebitService debitService;

    @Inject
    protected AssetRepository assetRepository;
    
    @Inject
    protected DeviceService deviceService;

    @Inject
    protected CreditService creditService;

    @Inject
    protected DaemonAssetService daemonAssetService;

    @Inject
    protected MessageRepository messageRepository;



    public abstract void handle(Transaction tx, T message);

    public abstract void handle(Transaction tx);
}
