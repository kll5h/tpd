package com.tilepay.core.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tilepay.core.model.Account;
import com.tilepay.core.repository.AccountRepository;

@Service
public class LoginService implements UserDetailsService {

    @Inject
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String passPhrase) throws UsernameNotFoundException {
        List<Account> accountList = accountRepository.findAll();
        Account account = findValidAccount(accountList, passPhrase);
        if (account == null) {
            throw new UsernameNotFoundException(" Error in retrieving user(username=" + passPhrase + ")");
        }
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
        return new org.springframework.security.core.userdetails.User(account.getPassPhrase(), account.getPassPhrase(), authorities);
    }

    private Account findValidAccount(List<Account> accountList, String passPhrase) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        for (Account account : accountList) {
            if (encoder.matches(passPhrase, account.getPassPhrase()))
                return account;
        }
        return null;
    }
}