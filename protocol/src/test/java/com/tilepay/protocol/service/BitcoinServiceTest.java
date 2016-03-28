package com.tilepay.protocol.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;

import java.io.File;
import java.math.BigInteger;

import javax.inject.Inject;

import org.bitcoinj.core.Wallet;
import org.bitcoinj.kits.WalletAppKit;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.IssuanceBuilder;
import com.tilepay.protocol.config.NetworkParametersConfig;
import com.tilepay.protocol.config.ProtocolConfig;

@ContextConfiguration(classes = ProtocolConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BitcoinServiceTest {

    @Inject
    private IssuanceService issuanceService;

    @Inject
    private BitcoinService bitcoinService;

    @Inject
    private NetworkParametersConfig networkParametersConfig;

    @Ignore
    @Test
    public void createTransaction() throws Exception {

        File walletDirectory = new File("C:/Users/andrei.sljusar/AppData/Local/Temp/protocol");

        WalletAppKit walletAppKit = new WalletAppKit(networkParametersConfig.networkParameters(), walletDirectory, "testnet");
        // WalletAppKit walletAppKit = new
        // WalletAppKit(networkParametersConfig.networkParameters(),
        // walletDirectory, "testnet1");
        walletAppKit.startAsync().awaitRunning();

        Wallet wallet = walletAppKit.wallet();
        String source = "my7TMYXdUzHMyeJXZUuS7563QzQxfmZRLg";
        String destination = "mi54TSEJt8QNBGc6rd7YwbXzfpQsTVhNbS";
        // TODO: 23.12.2014 Andrei Sljusar: UI
        Issuance issuance = IssuanceBuilder.anIssuance().setAsset(anAsset().setName("BBBB").build()).setQuantity(BigInteger.valueOf(1000L)).build();
        issuanceService.compose(issuance);

        //bitcoinService.send(wallet, source, destination, issuance, "123456");

        Thread thread = new Thread(() -> {
            while (true) {
            }
        });
        thread.start();
        thread.join();
    }

}