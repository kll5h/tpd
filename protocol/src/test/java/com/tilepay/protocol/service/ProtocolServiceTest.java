package com.tilepay.protocol.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;

import javax.inject.Inject;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.script.Script;
import org.junit.Test;

import com.tilepay.protocol.config.NetworkParametersConfig;

public class ProtocolServiceTest extends AbstractServiceTest {

    @Inject
    private ProtocolService protocolService;

    @Inject
    private CounterpartyMessageScriptBuilder counterpartyMessageScriptBuilder;

    @Inject
    private NetworkParametersConfig networkParametersConfig;

    @Inject
    private ByteService byteService;

    @Test
    public void isTilecoinProtocol() {
        boolean tilecoinProtocol = protocolService.isTilecoinProtocol("XTILECOIN".getBytes());
        assertFalse(tilecoinProtocol);

        tilecoinProtocol = protocolService.isTilecoinProtocol("TILECOIN".getBytes());
        assertTrue(tilecoinProtocol);
    }

    @Test
    public void getProtocolPrefix() {
        byte[] data = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        byte[] expectedData = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };
        
        assertArrayEquals(expectedData, protocolService.getProtocolPrefix(data, expectedData.length));
    }

    @Test
    public void extract() throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(62);
        byteService.putBytes(byteBuffer, 0, "expected".getBytes());
        byte[] expected = byteBuffer.array().clone();

        Transaction tx = new Transaction(networkParametersConfig.networkParameters());
        tx.addOutput(Coin.SATOSHI, new Address(networkParametersConfig.networkParameters(), "mjbkKLzKMdLq4FZoRCLTagtgR6sjgPs6ZM"));

        TransactionOutput prevOut = new Transaction(networkParametersConfig.networkParameters())
                .addOutput(Coin.SATOSHI, new Address(networkParametersConfig.networkParameters(), "mv9AHx4vdn9Mdxxpe5g8B3AxfAk77VMYx9"));
        Wallet wallet = new Wallet(networkParametersConfig.networkParameters());
        DeterministicKey deterministicKey = wallet.freshReceiveKey();

        tx.addSignedInput(prevOut, deterministicKey);

        String parentTxHash = protocolService.getParentTxHash(tx);
        Script script = counterpartyMessageScriptBuilder.buildScript(new byte[] { 1 }, byteBuffer.array(), parentTxHash);

        byte[] actual = protocolService.extract(tx, script.getChunks());

        assertArrayEquals(expected, actual);
    }
}