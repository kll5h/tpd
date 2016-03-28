package com.tilepay.core.dto;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.bitcoinj.core.Wallet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tilepay.core.config.CoreAppConfig;
import com.tilepay.core.config.NetworkParams;

@ContextConfiguration(classes = CoreAppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unittest")
public class WalletConverterTest {

    @Inject
    private WalletConverter walletConverter;

    @Inject
    private NetworkParams networkParams;

    @Test
    public void walletHasPassPhrase() {
        Wallet wallet = new Wallet(networkParams.get());
        WalletDTO walletDTO = walletConverter.getWalletDto(wallet, "1QEJvxk9nFdJaMcsF27sVorncUcANmnCR9", null);
        assertNotNull(walletDTO.getPassPhrase());
    }

    @Test
    public void encryptedWalletHasPrivateKey() {
        String password = "password";
        Wallet wallet = new Wallet(networkParams.get());
        wallet.encrypt(password);
        WalletDTO walletDTO = walletConverter.getWalletDto(wallet, wallet.currentReceiveAddress().toString(), password);
        assertNotNull(walletDTO.getPrivateKey());
    }

}