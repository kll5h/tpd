package com.tilepay.protocol.service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.DeterministicSeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.tilepay.domain.entity.DeviceRegistration;
import com.tilepay.domain.entity.DeviceRegistrationBuilder;
import com.tilepay.domain.entity.Message;
import com.tilepay.protocol.CoinConstants;
import com.tilepay.protocol.config.NetworkParametersConfig;

@Service("protocolBitcoinService")
public class BitcoinService {

    private static final Logger log = LoggerFactory.getLogger(BitcoinService.class);

    @Inject
    private NetworkParametersConfig networkParametersConfig;

    @Inject
    private CounterpartyMessageScriptBuilder counterpartyMessageScriptBuilder;
    
    @Inject
    private TilepayOPReturnMessageScriptBuilder tilepayOPReturnMessageScriptBuilder;

    @Inject
    private TilepayMessageScriptBuilder tilepayMessageScriptBuilder;
    
    @Inject
    private DeviceRegistrationService deviceRegistrationService;

    public Transaction createTransaction(Message messsage, byte[] sourcePubKey, String quantity, String destination, String inputHash) {
        Transaction tx = new Transaction(networkParametersConfig.networkParameters());
        Address destinationAddress = getAddress(destination);

        if (messsage.getAsset().isBitcoin()) {
            tx.addOutput(Coin.parseCoin(quantity), destinationAddress);
        } else if (messsage.getAsset().isCntrprty()) {
            tx.addOutput(CoinConstants.TX_DUST_IN_COINS, destinationAddress);
            tx.addOutput(CoinConstants.TX_DUST_IN_COINS, counterpartyMessageScriptBuilder.buildScript(sourcePubKey, messsage.getData(), inputHash));
        } else if (messsage.getAsset().isTilecoin()) {
            tx.addOutput(CoinConstants.TX_DUST_IN_COINS, destinationAddress);
            tx.addOutput(CoinConstants.TX_DUST_IN_COINS, tilepayMessageScriptBuilder.buildScript(sourcePubKey, messsage.getData(), null));
        } else {
            throw new IllegalArgumentException("Unknown protocol: " + messsage.getAsset().getProtocol());
        }

        return tx;
    }
   
    public Transaction createOPReturnTransaction(String registrationToken){
    	Transaction tx = new Transaction(networkParametersConfig.networkParameters());
    	
    	DeviceRegistration deviceRegistration = DeviceRegistrationBuilder.aDeviceRegistration().setRegistrationToken(registrationToken).build();
    	deviceRegistrationService.compose(deviceRegistration);
    	
        tx.addOutput(Coin.ZERO, tilepayOPReturnMessageScriptBuilder.buildScript(null,deviceRegistration.getData(),null));
        
        return tx;
    }
    
    public Wallet.SendResult sendOPReturnTransaction(Wallet wallet, String token, String source, String password, String minersFee, TransactionSentCallback transactionSentCallback){
    	Transaction tx = createOPReturnTransaction(token);
        return sendCoins(wallet, tx, transactionSentCallback, source, password, minersFee);
    }

    public Wallet.SendResult sendTransaction(Wallet wallet, String source, String destination, String quantity, String inputHash, Message message, String password, String minersFee,
            TransactionSentCallback transactionSentCallback) {
        byte[] sourcePubKey = getPubKey(wallet, source);
        //TODO: 16.01.2015 Andrei Sljusar: quantity is needed only for Bitcoin
        //TODO: 16.01.2015 Andrei Sljusar: inputHash is needed only for Counterparty
        Transaction tx = createTransaction(message, sourcePubKey, quantity, destination, inputHash);
        return sendCoins(wallet, tx, transactionSentCallback, source, password, minersFee);
    }

    public Wallet.SendResult send(Wallet wallet, com.tilepay.domain.entity.Transaction transaction, String password, TransactionSentCallback transactionSentCallback) {
        byte[] sourcePubKey = getPubKey(wallet, transaction.getSource());
        Transaction tx = createTransaction(transaction.getMessage(), sourcePubKey, null, transaction.getDestination(), null);
        Wallet.SendRequest sendRequest = Wallet.SendRequest.forTx(tx);
        sendRequest.aesKey = wallet.getKeyCrypter().deriveKey(password);
        sendRequest.feePerKb = Coin.ZERO;
        sendRequest.fee = CoinConstants.ASSET_CREATION_FEE_IN_COINS;
        sendRequest.shuffleOutputs = false;
        sendRequest.changeAddress = getAddress(transaction.getSource());
        return getSendResult(wallet, transactionSentCallback, sendRequest);
    }

    public Wallet.SendResult sendCoins(Wallet wallet, Transaction tx, TransactionSentCallback transactionSentCallback, String source, String password, String minersFee) {
        Wallet.SendRequest sendRequest = Wallet.SendRequest.forTx(tx);
        sendRequest.aesKey = wallet.getKeyCrypter().deriveKey(password);
        sendRequest.feePerKb = Coin.ZERO;
        sendRequest.fee = Coin.parseCoin(minersFee);
        sendRequest.shuffleOutputs = false;
        sendRequest.changeAddress = getAddress(source);
        return getSendResult(wallet, transactionSentCallback, sendRequest);
    }

    private Wallet.SendResult getSendResult(Wallet wallet, TransactionSentCallback transactionSentCallback, Wallet.SendRequest sendRequest) {
        try {
            Wallet.SendResult sendResult = wallet.sendCoins(sendRequest);
            Executor executor = Executors.newSingleThreadExecutor();
            sendResult.broadcastComplete.addListener(() -> {
                log.info("Sent coins onwards! Transaction hash is " + sendResult.tx.getHashAsString());
            }, executor);
            transactionSentCallback.finish("Sending successful. Transaction hash is " + sendResult.tx.getHashAsString());
            return sendResult;
        } catch (InsufficientMoneyException e) {
            transactionSentCallback.finish(e.getMessage());
            log.error("InsufficientMoneyException", e);
        } catch (Wallet.DustySendRequested e) {
            transactionSentCallback.finish(e.getMessage());
            log.error("DustySendRequested", e);
        }

        return null;
    }

    public String getWalletPassPhrase(Wallet wallet) {
        DeterministicSeed seed = wallet.getKeyChainSeed();
        return Joiner.on(" ").join(seed.getMnemonicCode());
    }

    public DeterministicKey getDeterministicKey(Wallet wallet, Address address) {
        return (DeterministicKey) wallet.findKeyFromPubHash(address.getHash160());
    }

    public DeterministicKey getDeterministicKey(Wallet wallet, String address) {
        try {
            return getDeterministicKey(wallet, new Address(networkParametersConfig.networkParameters(), address));
        } catch (AddressFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getPubKey(Wallet wallet, String address) {
        DeterministicKey deterministicKey = getDeterministicKey(wallet, address);
        return deterministicKey.getPubKey();
    }

    public String getPrivateKey(Wallet wallet, String address, String password) {
        DeterministicKey deterministicKey = getDeterministicKey(wallet, address).decrypt(wallet.getKeyCrypter().deriveKey(password));
        return deterministicKey.getPrivateKeyEncoded(networkParametersConfig.networkParameters()).toString();
    }

    public Address getAddress(String address) {
        try {
            return new Address(networkParametersConfig.networkParameters(), address);
        } catch (AddressFormatException e) {
            throw new RuntimeException(e);
        }
    }

}
