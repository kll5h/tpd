package com.tilepay.core.service;

import static com.google.common.util.concurrent.Service.State.STARTING;

import java.io.File;
import java.lang.reflect.Field;

import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.bitcoinj.kits.WalletAppKit;
import org.springframework.stereotype.Component;

import com.tilepay.core.config.NetworkParams;

@Component
@ThreadSafe
@Singleton
public class WalletAppKitHolder {

    private WalletAppKit walletAppKit;

    @Inject
    private NetworkParams networkParams;

    @Inject
    private AppDataDirectoryService appDataDirectoryService;

    private WalletAppKitHolder() {
    }

    public WalletAppKit getWalletAppKit(Long walletId) {
        String filePrefix = networkParams.getWalletPrefix() + "-wallet-" + walletId;

        if (walletAppKit == null) {
            startWallet(filePrefix);
        } else {
            if (!currentFilePrefix().equals(filePrefix)) {
                walletAppKit.stopAsync().awaitTerminated();
                startWallet(filePrefix);
            }
        }

        return walletAppKit;
    }

    private void startWallet(String filePrefix) {
        File walletDirectory = new File(appDataDirectoryService.getDataDirectory());

        walletAppKit = new WalletAppKit(networkParams.get(), walletDirectory, filePrefix);
        walletAppKit.startAsync().awaitRunning();
        walletAppKit.wallet().setCoinSelector(new OrderedCoinSelector());
    }

    private String currentFilePrefix() {
        try {
            Field field = WalletAppKit.class.getDeclaredField("filePrefix");
            field.setAccessible(true);
            return field.get(walletAppKit).toString();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isStarting() {
        if (walletAppKit != null) {
            if (walletAppKit.state() == STARTING) {
                return true;
            }
        }
        return false;
    }

    public void encryptWallet(Long walletId, String password) {
        WalletAppKit kit = getWalletAppKit(walletId);
        if (kit != null && !kit.wallet().isEncrypted()) {
            kit.wallet().encrypt(password);
        }
    }
}
