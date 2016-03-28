package com.tilepay.core.repository;

import com.tilepay.core.model.Account;
import com.tilepay.core.model.IndividualAccount;
import com.tilepay.core.model.Wallet;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

public class AccountRepositoryTest extends AbstractRepositoryTest {

    @Inject
    private AccountRepository accountRepository;

    @Test
    public void findAll() {
        accountRepository.findAll();
    }

    @Test
    public void save() {
        Account account = new IndividualAccount();
        account.setPassPhrase("12345");
        Wallet wallet = new Wallet();
        account.setWallet(wallet);

        accountRepository.save(account);

        Account actualAccount = accountRepository.findOne(account.getId());
        assertEquals(account.getId(), actualAccount.getId());
        assertEquals("12345", actualAccount.getPassPhrase());
        assertEquals(wallet.getId(), actualAccount.getWallet().getId());
    }


}