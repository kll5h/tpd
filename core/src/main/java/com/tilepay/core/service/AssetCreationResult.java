package com.tilepay.core.service;

import org.bitcoinj.core.Wallet;

public class AssetCreationResult {
    public Wallet.SendResult assetCreationTxSendResult;
    public Wallet.SendResult feeTxSendResult;
}
