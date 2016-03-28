package com.tilepay.core.model;

import static com.tilepay.core.model.AccountType.COMPANY;
import static com.tilepay.core.model.AccountType.INDIVIDUAL;


public class AccountFactory {

    public static Account createAccount(Account rawAccount) {
        AccountType accountType = rawAccount.getAccountType();
        Account account;
        if (accountType.equals(INDIVIDUAL)) {
            account = new IndividualAccount();
        } else if (accountType.equals(COMPANY)) {
            account = new CompanyAccount();
        } else {
            throw new RuntimeException("Unknown account type: " + accountType);
        }

        account.setPassPhrase(rawAccount.getPassPhrase());
        account.setWallet(rawAccount.getWallet());
        return account;
    }
}
