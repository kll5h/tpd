package com.tilepay.core.dto;

import javax.inject.Inject;

import org.bitcoinj.core.Wallet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class WalletConverter {

    @Inject
    @Qualifier("protocolBitcoinService")
    private com.tilepay.protocol.service.BitcoinService protocolBitcoinService;

    public WalletDTO getWalletDto(Wallet wallet, String address, String password) {
        WalletDTO walletDto = getWalletDto(wallet);

        if (password != null) {
            String privateKey = protocolBitcoinService.getPrivateKey(wallet, address, password);
            walletDto.setPrivateKey(privateKey);
        } else if (!wallet.isEncrypted()) {
            walletDto.setPassPhrase(protocolBitcoinService.getWalletPassPhrase(wallet));
        }

        walletDto.setReceiveAddress(address);
        return walletDto;
    }

    private WalletDTO getWalletDto(Wallet wallet) {
        WalletDTO walletDto = new WalletDTO();
        walletDto.setBalanceAvailable(wallet.getBalance().toPlainString());
        walletDto.setBalanceEstimated(wallet.getBalance(Wallet.BalanceType.ESTIMATED).toPlainString());
        return walletDto;
    }

}
