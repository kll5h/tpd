package com.tilepay.core.model;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountFactoryTest {

    @Test
    public void createIndividualAccount() {
        assertCreateAccount(AccountType.INDIVIDUAL, IndividualAccount.class);
    }

    @Test
    public void createCompanyAccount() throws Exception {
        assertCreateAccount(AccountType.COMPANY, CompanyAccount.class);
    }

    public void assertCreateAccount(AccountType accountType, Class<? extends Account> cls) {
        Account account = new Account();
        Address address = new Address();
        Wallet wallet = new Wallet();
        address.setAddress("987654321");
        account.setWallet(wallet);
        account.getWallet().setAddress(address);
        account.setPassPhrase("123456789");
        account.setAccountType(accountType);

        Account actualAccount = AccountFactory.createAccount(account);

        assertTrue(actualAccount.getClass().isAssignableFrom(cls));
        assertEquals("123456789", actualAccount.getPassPhrase());
        //TODO: 04.10.2014 Andrei Sljusar: ?
        //assertEquals("987654321", actualAccount.getWallet().getAddresses().iterator().next().getAddress());
    }


}