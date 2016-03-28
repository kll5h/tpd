package com.tilepay.core.service;

import com.tilepay.core.config.CoreAppConfig;
import com.tilepay.core.model.Account;
import com.tilepay.core.model.AccountType;
import com.tilepay.core.model.Address;
import com.tilepay.core.model.Wallet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreAppConfig.class)
@ActiveProfiles("unittest")
public class LoginServiceTest {

    @Inject
    private LoginService loginService;

    @Inject
    private AccountService accountService;

    @Test
    public void loadUserByUsername() throws Exception {
        Account account = new Account();
        Address address = new Address();
        Wallet wallet = new Wallet();
        address.setAddress("987654321");
        account.setWallet(wallet);
        account.getWallet().setAddress(address);
        account.setPassPhrase("12345");
        account.setAccountType(AccountType.INDIVIDUAL);

        accountService.createAccount(account);

        UserDetails user = loginService.loadUserByUsername("12345");
        assertNotNull(user);
    }
}