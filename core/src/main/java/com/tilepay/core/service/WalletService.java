package com.tilepay.core.service;

import static com.google.common.util.concurrent.Service.State.STARTING;

import javax.inject.Inject;

import org.bitcoinj.kits.WalletAppKit;
import org.springframework.stereotype.Service;

import com.tilepay.core.dto.WalletConverter;
import com.tilepay.core.dto.WalletDTO;
import com.tilepay.core.model.Account;
import com.tilepay.core.model.Address;
import com.tilepay.core.model.Wallet;
import com.tilepay.core.repository.WalletRepository;

@Service
public class WalletService {

    @Inject
    private WalletRepository walletRepository;

    @Inject
    private AccountService accountService;

    @Inject
    private WalletConverter walletConverter;

    @Inject
    private WalletAppKitHolder walletAppKitHolder;

    public Long getWalletMaxId() {
        return walletRepository.selectWalletMaxId();
    }

    public Wallet createNewWalletForAccount() {
        Wallet wallet = new Wallet();
        walletRepository.save(wallet);
        //TODO: 13.11.2014 Andrei Sljusar: walletID
        WalletDTO walletDTO = createWallet(wallet.getId());
        Address receivingAddress = new Address();
        receivingAddress.setAddress(walletDTO.getReceiveAddress());
        receivingAddress.setWallet(wallet);
        wallet.setAddress(receivingAddress);

        walletRepository.save(wallet);
        return wallet;
    }

    public WalletDTO loadWallet(Long accountId, String password) {
        Account account = accountService.findOne(accountId);
        String address = account.getWallet().getAddress().getAddress();
        WalletAppKit kit = walletAppKitHolder.getWalletAppKit(account.getWallet().getId());
        return walletConverter.getWalletDto(kit.wallet(), address, password);
    }
    
    public WalletDTO loadWallet(Long accountId, String password, String address) {
        Account account = accountService.findOne(accountId);
        WalletAppKit kit = walletAppKitHolder.getWalletAppKit(account.getWallet().getId());
        return walletConverter.getWalletDto(kit.wallet(), address, password);
    }

    public WalletDTO createWallet(Long walletId) {
        WalletAppKit kit = walletAppKitHolder.getWalletAppKit(walletId);
        // TODO: 14.12.2014 Andrei Sljusar: move to WalletAppKitHolder
        if (kit.state() == STARTING) {
            kit.awaitRunning();
        }
        String address = kit.wallet().currentReceiveAddress().toString();
        return walletConverter.getWalletDto(kit.wallet(), address, null);
    }

    public WalletDTO createWallet() {
        Long walletId = getWalletMaxId() + 1;
        return createWallet(walletId);
    }

    public void encryptWallet(Long walletId, String password) {
        walletAppKitHolder.encryptWallet(walletId, password);
    }
    
    public String addAddressToWallet(Long walletId){
    	WalletAppKit appKit= walletAppKitHolder.getWalletAppKit(walletId);
    	org.bitcoinj.core.Address newAddress = appKit.wallet().freshReceiveAddress();
    	
    	System.out.println(appKit.wallet());
    	return newAddress.toString();
    }
}
