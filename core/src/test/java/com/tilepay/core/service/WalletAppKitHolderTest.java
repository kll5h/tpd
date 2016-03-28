package com.tilepay.core.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.inject.Inject;

//import org.apache.commons.io.FileUtils;
import org.bitcoinj.kits.WalletAppKit;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tilepay.core.config.CoreAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreAppConfig.class)
@ActiveProfiles("unittest")
public class WalletAppKitHolderTest {

    @Inject
    WalletAppKitHolder walletAppKitHolder;

    @Before
    public void before() {

        new File("C:/Users/Mait/AppData/Roaming/tilepay/unittest-wallet-3.spvchain").delete();
        new File("C:/Users/Mait/AppData/Roaming/tilepay/unittest-wallet-3.wallet").delete();

    }

    @Test
    @Ignore("How to mock walletAppKit?")
    public void isStarting() {
        assertFalse(walletAppKitHolder.isStarting());

        // TODO: Dec 11, 2014 Mait Mikkelsaar: Mock app kit STARTING
        WalletAppKit kit = walletAppKitHolder.getWalletAppKit(3L);
        assertTrue(walletAppKitHolder.isStarting());
    }

}
