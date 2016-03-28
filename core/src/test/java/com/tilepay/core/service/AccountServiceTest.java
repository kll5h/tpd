package com.tilepay.core.service;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tilepay.core.config.CoreAppConfig;
import com.tilepay.core.model.Account;
import com.tilepay.core.model.AccountBuilder;
import com.tilepay.core.model.AccountType;
import com.tilepay.core.model.Wallet;
import com.tilepay.core.repository.AccountRepository;

@ContextConfiguration(classes = CoreAppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unittest")
public class AccountServiceTest {

    @Inject
    private AccountService accountService;

    @Inject
    private AccountRepository accountRepository;

    @Test
    public void createAndSave() {
        Wallet wallet = new Wallet();
        Account account = AccountBuilder.anAccount().setPassphrase("12345").setAccountType(AccountType.INDIVIDUAL).setWallet(wallet).build();

        Account expectedAccount = accountService.createAccount(account);
        Account actualAccount = accountRepository.findOne(expectedAccount.getId());

        assertEquals(expectedAccount.getId(), actualAccount.getId());
    }
}