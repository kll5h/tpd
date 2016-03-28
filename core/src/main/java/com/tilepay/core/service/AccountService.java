package com.tilepay.core.service;

import javax.inject.Inject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tilepay.core.model.Account;
import com.tilepay.core.model.AccountFactory;
import com.tilepay.core.repository.AccountRepository;

@Service
public class AccountService {

    @Inject
    private AccountRepository accountRepository;

    public void save(Account account) {
        this.accountRepository.save(account);
    }

    public Account findOne(Long id) {
        return accountRepository.getOne(id);
    }

    public Account findOneByPassword(String password) {
        for (Account account : accountRepository.findAll()) {
            if (account.getPassPhrase().equals(password))
                return account;
        }
        return null;
    }

    @Transactional
    public Account createAccount(Account account) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassPhrase = encoder.encode(account.getPassPhrase());
        account.setPassPhrase(hashedPassPhrase);
        return createAndSave(account);
    }

    private Account createAndSave(Account account) {
        Account accountToBeSaved = AccountFactory.createAccount(account);
        this.accountRepository.save(accountToBeSaved);
        return accountToBeSaved;
    }
}
