package com.tilepay.web.service;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.kits.WalletAppKit;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.tilepay.core.service.WalletAppKitHolder;

@Component
public class PasswordValidator {

    @Inject
    private SessionService sessionService;

    @Inject
    private WalletAppKitHolder walletAppKitHolder;

    public boolean validatePassword(String password, Errors errors) {

        if (StringUtils.isBlank(password)) {
            errors.rejectValue("password", "empty.password");
            return false;
        }

        boolean passwordCorrect = isPasswordCorrect(sessionService.getAccountId(), password);
        if (!passwordCorrect) {
            errors.rejectValue("password", "incorrect.password");
        }
        return passwordCorrect;
    }

    public boolean isPasswordCorrect(Long walletId, String password) {
        WalletAppKit kit = walletAppKitHolder.getWalletAppKit(walletId);
        Wallet wallet = kit.wallet();

        if (wallet.isEncrypted()) {
            return wallet.checkPassword(password);
        }

        throw new RuntimeException("You don't have to check password for unencrypted wallet. Wallet id: " + walletId);
    }
}
