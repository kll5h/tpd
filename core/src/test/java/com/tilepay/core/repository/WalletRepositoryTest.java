package com.tilepay.core.repository;

import com.tilepay.core.model.Wallet;
import org.junit.Test;

import javax.inject.Inject;

public class WalletRepositoryTest extends AbstractRepositoryTest {

    @Inject
    private WalletRepository walletRepository;

    @Test
    public void save() {
        Wallet wallet = new Wallet();
        walletRepository.save(wallet);
    }

}