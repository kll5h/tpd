package com.tilepay.protocol.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.IssuanceBuilder.anIssuance;
import static org.bitcoinj.script.ScriptOpCodes.OP_CHECKMULTISIG;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import javax.inject.Inject;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptOpCodes;
import org.junit.Test;

import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.Message;
import com.tilepay.protocol.config.NetworkParametersConfig;

public class ProtocolTransactionServiceTest extends AbstractServiceTest {

    @Inject
    private ProtocolTransactionService transactionService;

    @Inject
    private IssuanceService issuanceService;

    @Inject
    private NetworkParametersConfig networkParametersConfig;

    //TODO: 31.12.2014 Andrei Sljusar: test parse Send
    @Test
    public void parse() throws Exception {
        Transaction tx = new Transaction(networkParametersConfig.networkParameters());

        Address destination = new Address(networkParametersConfig.networkParameters(), "mjbkKLzKMdLq4FZoRCLTagtgR6sjgPs6ZM");
        tx.addOutput(Coin.parseCoin("0.0000078"), destination);

        Issuance issuance = anIssuance().setAsset(anAsset().setName("BBBB").setDivisible(true).build()).setQuantity(new BigInteger("1000")).build();
        issuanceService.compose(issuance);

        Script script = new ScriptBuilder().op(ScriptOpCodes.OP_1).data("mv9AHx4vdn9Mdxxpe5g8B3AxfAk77VMYx9".getBytes()).data(issuance.getData()).op(ScriptOpCodes.OP_2).op(OP_CHECKMULTISIG).build();

        tx.addOutput(Coin.parseCoin("0.0000078"), script);

        TransactionOutput prevOut = new Transaction(networkParametersConfig.networkParameters())
                .addOutput(Coin.SATOSHI, new Address(networkParametersConfig.networkParameters(), "mv9AHx4vdn9Mdxxpe5g8B3AxfAk77VMYx9"));
        Wallet wallet = new Wallet(networkParametersConfig.networkParameters());
        DeterministicKey deterministicKey = wallet.freshReceiveKey();
        String expectedSourceAddress = deterministicKey.toAddress(networkParametersConfig.networkParameters()).toString();
        tx.addSignedInput(prevOut, deterministicKey);

        com.tilepay.domain.entity.Transaction transaction = transactionService.parse(100000,tx);

        assertEquals(tx.getHashAsString(), transaction.getHash());
        assertEquals(expectedSourceAddress, transaction.getSource());
        assertEquals("mjbkKLzKMdLq4FZoRCLTagtgR6sjgPs6ZM", transaction.getDestination());
        assertNotNull(transaction.getData());
        assertArrayEquals(issuance.getData(), transaction.getData());

        Message actualMessage = transaction.getMessage();
        assertEquals("BBBB", actualMessage.getAssetName());
        assertEquals(1000, actualMessage.getQuantity().intValue());
        assertEquals(true, actualMessage.getAsset().getDivisible());
    }

    @Test
    public void getDestinationAddress() throws AddressFormatException {
        Transaction tx = new Transaction(networkParametersConfig.networkParameters());
        TransactionOutput transactionOutput = tx.addOutput(Coin.parseCoin("0.0000078"), new Address(networkParametersConfig.networkParameters(), "mjbkKLzKMdLq4FZoRCLTagtgR6sjgPs6ZM"));
        String destinationAddress = transactionService.getDestinationAddress(transactionOutput);
        assertEquals("mjbkKLzKMdLq4FZoRCLTagtgR6sjgPs6ZM", destinationAddress);
    }

    @Test
    public void getSourceAddress() throws Exception {

        Transaction tx = new Transaction(networkParametersConfig.networkParameters());
        tx.addOutput(Coin.SATOSHI, new Address(networkParametersConfig.networkParameters(), "mjbkKLzKMdLq4FZoRCLTagtgR6sjgPs6ZM"));

        TransactionOutput prevOut = new Transaction(networkParametersConfig.networkParameters())
                .addOutput(Coin.SATOSHI, new Address(networkParametersConfig.networkParameters(), "mv9AHx4vdn9Mdxxpe5g8B3AxfAk77VMYx9"));

        Wallet wallet = new Wallet(networkParametersConfig.networkParameters());
        DeterministicKey deterministicKey = wallet.freshReceiveKey();
        Address expectedSourceAddress = deterministicKey.toAddress(networkParametersConfig.networkParameters());

        tx.addSignedInput(prevOut, deterministicKey);

        String actualSourceAddress = transactionService.getSourceAddress(tx.getInput(0));
        assertEquals(expectedSourceAddress.toString(), actualSourceAddress);
    }

}