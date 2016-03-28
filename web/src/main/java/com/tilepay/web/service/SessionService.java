package com.tilepay.web.service;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tilepay.core.model.Account;
import com.tilepay.core.model.CompanyAccount;
import com.tilepay.core.model.IndividualAccount;
import com.tilepay.core.repository.AccountRepository;
import com.tilepay.core.service.AccountService;

@Service
public class SessionService {

    @Autowired
    private SessionData session;

    @Autowired
    private AccountRepository accountRepository;

    @Inject
    private AccountService accountService;

    public Account getAccount() {
        if (session.getAccountId() == null) {
            return null;
        }
        return accountRepository.findOne(session.getAccountId());
    }

    public Long getAccountId() {
        return session.getAccountId();
    }

    public IndividualAccount getCurrentIndividualAccount() {
        return (IndividualAccount) accountService.findOne(getAccountId());
    }

    public CompanyAccount getCurrentCompanyAccount() {
        return (CompanyAccount) accountService.findOne(getAccountId());
    }

}
